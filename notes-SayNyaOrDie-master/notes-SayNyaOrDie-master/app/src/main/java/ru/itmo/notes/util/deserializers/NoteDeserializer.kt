package ru.itmo.notes.util.deserializers

import ru.itmo.notes.domain.entities.Folder
import ru.itmo.notes.domain.entities.Note

object NoteDeserializer : Deserializer<Note> {
    private val reg =
        Regex("Note[(]noteId=(\\d+),folderId=(\\d+),isInTrashBin=(false|true),content=\"(.*)\"[)]")

    override fun deserialize(string: String): Note? {
        val values = reg.find(string)?.groupValues ?: return null
        val noteId = values[1].toLongOrNull() ?: return null
        val folderId = values[2].toLongOrNull() ?: return null
        val isInTrashBin = values[3].toBooleanStrictOrNull() ?: return null
        val content = values[4]
        return Note(
            id = Note.ID(noteId),
            folderId = Folder.ID(folderId),
            isInTrashBin = isInTrashBin,
            content = content,
        )
    }
}