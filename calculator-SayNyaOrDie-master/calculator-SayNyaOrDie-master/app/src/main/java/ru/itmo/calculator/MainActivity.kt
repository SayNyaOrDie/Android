package ru.itmo.calculator

import Parser
import android.app.AlertDialog
import android.content.res.Configuration
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity


class MainActivity : ComponentActivity() {

    private lateinit var expressionTextView: TextView
    private lateinit var resultTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        expressionTextView = findViewById(R.id.info_text_view)
        resultTextView = findViewById(R.id.output_text_view)

        initializeButtons()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        setContentView(R.layout.main_activity)

        expressionTextView = findViewById(R.id.info_text_view)
        resultTextView = findViewById(R.id.output_text_view)

        initializeButtons()
    }

    private fun initializeButtons() {
        val buttonIds = arrayOf(
            R.id.button_0, R.id.button_1, R.id.button_2, R.id.button_3,
            R.id.button_4, R.id.button_5, R.id.button_6, R.id.button_7,
            R.id.button_8, R.id.button_9, R.id.button_dot, R.id.button_ln,
            R.id.opened_bracket, R.id.closed_bracket, R.id.button_X, R.id.button_Y,
            R.id.button_Z, R.id.multiply, R.id.division, R.id.plus, R.id.minus,
            R.id.sqrt, R.id.button_clear, R.id.button_evaluate
        )

        for (buttonId in buttonIds) {
            val button: Button = findViewById(buttonId)
            button.setOnClickListener { actionButton(button) }
        }
    }

    private fun actionButton(button: Button) {
        val buttonText = button.text.toString()

        when (buttonText) {
            "C" -> clearTextFields()
            "=" -> {
                if (expressionTextView.text.toString().contains("X")) {
                    showVariableInputDialog(listOf("X"))
                    return
                }
                if (expressionTextView.text.toString().contains("Y")) {
                    showVariableInputDialog(listOf("Y"))
                    return
                }
                if (expressionTextView.text.toString().contains("Z")) {
                    showVariableInputDialog(listOf("Z"))
                    return
                }
                calculation()
            }
            else -> appendTextToExpression(buttonText)
        }
    }

    private fun clearTextFields() {
        expressionTextView.text = ""
        resultTextView.text = ""
    }

    private fun appendTextToExpression(text: String) {
        val currentExpression = expressionTextView.text.toString()
        expressionTextView.text = "$currentExpression$text"
    }

    private fun calculation() {
        val expression = expressionTextView.text.toString()

        try {
            val parser = Parser()

            val result = parser.parse(expression)

            resultTextView.text = result.toString()
        } catch (e: Exception) {
            resultTextView.text = "Error: ${e.message}"
        }
    }

    private fun showVariableInputDialog(variables: List<String>) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Enter values for ${variables.joinToString(", ")}")

        val inputLayout = LinearLayout(this)
        inputLayout.orientation = LinearLayout.VERTICAL

        val inputList = mutableListOf<EditText>()

        for (variable in variables) {
            val input = EditText(this)
            input.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
            input.hint = variable
            inputList.add(input)
            inputLayout.addView(input)
        }

        builder.setView(inputLayout)

        builder.setPositiveButton("OK") { _, _ ->
            val values = inputList.map { it.text.toString() }
            updateVariableValues(variables, values)
        }

        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }

        builder.show()
    }

    private fun updateVariableValues(variables: List<String>, values: List<String>) {
        var currentExpression = expressionTextView.text.toString()

        for (i in variables.indices) {
            currentExpression = currentExpression.replace(variables[i], values[i])
        }

        expressionTextView.text = currentExpression
    }
}
