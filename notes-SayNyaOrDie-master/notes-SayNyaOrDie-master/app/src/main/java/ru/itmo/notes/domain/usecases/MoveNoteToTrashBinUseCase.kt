package ru.itmo.notes.domain.usecases

import ru.itmo.notes.domain.entities.Folder
import ru.itmo.notes.domain.entities.Note
import ru.itmo.notes.domain.repositories.NotesRepository

class MoveNoteToTrashBinUseCase(private val notesRepository: NotesRepository) {
    /**
     * Moves note to trash bin.
     * Returns `true` if note was successfully moved.
     * Returns `false` if such not does not exists or it is already
     * in trash bin.
     */
    fun moveNoteToTrashBin(folderId: Folder.ID, noteId: Note.ID) =
        notesRepository.moveNoteToTrashBin(folderId, noteId)
}