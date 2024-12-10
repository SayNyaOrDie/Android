package ru.itmo.notes.domain.usecases

import ru.itmo.notes.domain.entities.Folder
import ru.itmo.notes.domain.repositories.FoldersRepository

class GetFoldersUseCase(
    private val foldersRepository: FoldersRepository
) {
    fun getFolders(): List<Folder> = foldersRepository.getFolders()
    fun getFolder(id: Folder.ID) = foldersRepository.getFolderById(id)
}