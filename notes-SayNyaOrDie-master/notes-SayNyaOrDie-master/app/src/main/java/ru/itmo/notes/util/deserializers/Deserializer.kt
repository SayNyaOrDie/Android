package ru.itmo.notes.util.deserializers

interface Deserializer<T> {
    fun deserialize(string: String): T?
}