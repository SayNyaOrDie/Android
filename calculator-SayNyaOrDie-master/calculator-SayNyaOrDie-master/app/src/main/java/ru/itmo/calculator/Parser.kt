import java.util.*
import kotlin.math.sqrt
import kotlin.math.ln

class Parser {
    fun parse(expression: String): Double {
        val postfixExpression = infixToPostfix(expression)
        return evaluatePostfix(postfixExpression)
    }

    private fun infixToPostfix(infix: String): List<String> {
        val output = mutableListOf<String>()
        val stack = Stack<String>()

        val operators = mapOf(
            "+" to 1, "-" to 1,
            "*" to 2, "/" to 2,
            "√" to 3, "LN" to 3
        )

        val tokens = infix.replace(" ", "").split(Regex("(?<=[-+*/√()])|(?=[-+*/√()])"))

        for (token in tokens) {
            when {
                token.matches(Regex("[0-9.]+")) -> output.add(token)
                token == "√" || token == "LN" -> stack.push(token)
                token == "(" -> stack.push(token)
                token == ")" -> {
                    while (stack.isNotEmpty() && stack.peek() != "(") {
                        output.add(stack.pop())
                    }
                    stack.pop()
                }
                operators.containsKey(token) -> {
                    while (stack.isNotEmpty() && operators.getOrElse(token, { 0 }) <= operators.getOrElse(stack.peek(), { 0 })) {
                        output.add(stack.pop())
                    }
                    stack.push(token)
                }
            }
        }

        while (stack.isNotEmpty()) {
            output.add(stack.pop())
        }

        return output
    }


    private fun evaluatePostfix(postfix: List<String>): Double {
        val stack = Stack<Double>()

        for (token in postfix) {
            when {
                token.matches(Regex("[0-9.]+")) -> stack.push(token.toDouble())
                token == "√" -> stack.push(sqrt(stack.pop()))
                token == "LN" -> stack.push(ln(stack.pop()))
                else -> {
                    val operand2 = stack.pop()
                    val operand1 = if (token == "√" || token == "LN") 0.0 else stack.pop()
                    stack.push(performOperation(operand1, operand2, token))
                }
            }
        }

        return stack.pop()
    }

    private fun performOperation(operand1: Double, operand2: Double, operator: String): Double {
        return when (operator) {
            "+" -> operand1 + operand2
            "-" -> operand1 - operand2
            "*" -> operand1 * operand2
            "/" -> operand1 / operand2
            "√" -> sqrt(operand2)
            "LN" -> ln(operand2)
            else -> throw IllegalArgumentException("Invalid operator: $operator")
        }
    }
}
