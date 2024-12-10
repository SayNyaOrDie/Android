package ru.itmo.notes.domain.usecases

import ru.itmo.notes.domain.entities.Folder
import ru.itmo.notes.domain.repositories.FoldersRepository

class EditFolderNameUseCase(private val foldersRepository: FoldersRepository) {
    fun setFolderName(folderID: Folder.ID, newName: String) =
        foldersRepository.setFolderName(folderID, newName)
}