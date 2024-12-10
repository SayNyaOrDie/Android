package ru.itmo.notes.util.serializers

import ru.itmo.notes.domain.entities.Note

object NoteSerializer : Serializer<Note> {
    override fun serialize(input: Note): String {
        return "Note(noteId=${input.id.value},folderId=${input.folderId.value},isInTrashBin=${input.isInTrashBin},content=\"${input.content}\")"
    }
}