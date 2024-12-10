package ru.itmo.notes.domain.entities

data class Folder(
    val id: ID,
    val name: String,
) {
    data class ID(val value: Long)
}