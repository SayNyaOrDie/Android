package ru.itmo.notes.domain.entities

data class Note(
    val id: ID,
    val folderId: Folder.ID,
    val content: String,
    val isInTrashBin: Boolean = false
) {
    data class ID(val value: Long)
}