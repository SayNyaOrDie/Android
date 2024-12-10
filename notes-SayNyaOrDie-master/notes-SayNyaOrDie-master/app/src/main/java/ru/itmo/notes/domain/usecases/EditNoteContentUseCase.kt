package ru.itmo.notes.domain.usecases

import ru.itmo.notes.domain.entities.Folder
import ru.itmo.notes.domain.entities.Note
import ru.itmo.notes.domain.repositories.NotesRepository

class EditNoteContentUseCase(
    private val notesRepository: NotesRepository
) {
    fun editNoteContent(folderId: Folder.ID, noteId: Note.ID, newContent: String) =
        notesRepository.editNoteContent(folderId, noteId, newContent)

    operator fun invoke(folderId: Folder.ID, noteId: Note.ID, newContent: String) =
        editNoteContent(folderId, noteId, newContent)
}