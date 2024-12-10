package ru.itmo.notes.domain.usecases

import ru.itmo.notes.domain.entities.Folder
import ru.itmo.notes.domain.entities.Note
import ru.itmo.notes.domain.repositories.NotesRepository

class RestoreNoteUseCase(private val notesRepository: NotesRepository) {
    fun restoreNote(folderId: Folder.ID, noteId: Note.ID): Boolean =
        notesRepository.restoreNote(folderId, noteId)

    operator fun invoke(folderId: Folder.ID, noteId: Note.ID) = restoreNote(folderId, noteId)
}