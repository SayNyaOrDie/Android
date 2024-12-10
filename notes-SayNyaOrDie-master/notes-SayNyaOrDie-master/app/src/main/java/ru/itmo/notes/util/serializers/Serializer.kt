package ru.itmo.notes.util.serializers

interface Serializer<T> {
    fun serialize(input: T): String
}