package ru.itmo.notes.data.repositories.folders

import android.util.Log
import ru.itmo.notes.domain.entities.Folder
import ru.itmo.notes.domain.repositories.FoldersRepository

class InMemoryFoldersRepository : FoldersRepository {
    private val folders = mutableListOf<Folder>()

    override fun getFolders(): List<Folder> {
        return folders.toList()
    }

    override fun getFolderById(folderId: Folder.ID): Folder? {
        println(folders)
        Log.i(this::class.simpleName, "Trying to find folder with id = ${folderId.value}")
        return folders.find { it.id == folderId }
    }

    override fun createFolder(name: String): Folder {
        val newId = Folder.ID((folders.maxByOrNull { it.id.value }?.id?.value ?: 0) + 1)
        val newFolder = Folder(newId, name)
        folders.add(newFolder)
        Log.i(this::class.simpleName, "Createw new folder $newFolder")
        return newFolder
    }

    override fun setFolderName(folderId: Folder.ID, folderName: String) {
        val index = this.folders.indexOfFirst { it.id == folderId }
        this.folders[index] = this.folders[index].copy(name = folderName)
    }

    override fun deleteFolder(folderId: Folder.ID): Boolean {
        return folders.removeIf { it.id == folderId }
    }
}