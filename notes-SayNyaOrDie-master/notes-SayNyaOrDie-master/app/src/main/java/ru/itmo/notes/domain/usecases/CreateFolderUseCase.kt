package ru.itmo.notes.domain.usecases

import ru.itmo.notes.domain.repositories.FoldersRepository

class CreateFolderUseCase(private val repository: FoldersRepository) {
    fun createFolder(name: String) = repository.createFolder(name)
}