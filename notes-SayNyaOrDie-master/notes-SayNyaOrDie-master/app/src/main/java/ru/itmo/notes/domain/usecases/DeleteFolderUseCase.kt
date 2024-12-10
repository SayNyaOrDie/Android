package ru.itmo.notes.domain.usecases

import ru.itmo.notes.domain.entities.Folder
import ru.itmo.notes.domain.repositories.FoldersRepository
import ru.itmo.notes.domain.repositories.NotesRepository

class DeleteFolderUseCase(
    private val foldersRepository: FoldersRepository,
    private val notesRepository: NotesRepository,
) {
    /**
     * Permanently deletes folder with given ID and all of its notes
     */
    fun deleteFolder(folderId: Folder.ID): Boolean {
        notesRepository.deleteNotesByFolder(folderId)
        return foldersRepository.deleteFolder(folderId)
    }

    operator fun invoke(folderId: Folder.ID) = deleteFolder(folderId)
}