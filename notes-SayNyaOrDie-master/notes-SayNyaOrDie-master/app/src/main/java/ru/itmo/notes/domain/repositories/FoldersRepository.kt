package ru.itmo.notes.domain.repositories

import ru.itmo.notes.domain.entities.Folder

interface FoldersRepository {
    fun getFolders(): List<Folder>
    fun getFolderById(folderId: Folder.ID): Folder?
    fun createFolder(name: String): Folder
    fun setFolderName(folderId: Folder.ID, folderName: String)
    fun deleteFolder(folderId: Folder.ID): Boolean
}