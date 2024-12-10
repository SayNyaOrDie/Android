package ru.itmo.notes.data.repositories.folders

import android.content.SharedPreferences
import android.util.Log
import ru.itmo.notes.domain.entities.Folder
import ru.itmo.notes.domain.repositories.FoldersRepository
import ru.itmo.notes.util.deserializers.FolderDeserializer
import ru.itmo.notes.util.serializers.FolderSerializer

class SharedPreferencesFoldersRepository(
    private val preferences: SharedPreferences
) : FoldersRepository {
    private val folders = mutableListOf<Folder>()

    private fun loadFromPreferences() {
        val newFolders = preferences
            .getStringSet("folders", emptySet())!!
            .map { parseFolder(it) }
            .sortedBy { it.id.value }
        folders.clear()
        folders.addAll(newFolders)
        Log.i(this::class.simpleName, "Loaded ${folders.size} folders from shared preferences")
    }

    init {
        loadFromPreferences()
    }

    private fun saveToPreferences() {
        val editor = preferences.edit()
        editor.putStringSet(
            "folders",
            folders.map { FolderSerializer.serialize(it) }.toSet()
        )
        editor.apply()
        Log.i(this::class.simpleName, "Saved ${folders.size} folders to shared preferences")
    }

    private fun parseFolder(s: String): Folder {
        return FolderDeserializer.deserialize(s)!!
    }

    override fun getFolders(): List<Folder> {
        return folders.toList()
    }

    override fun getFolderById(folderId: Folder.ID): Folder? {
        Log.i(this::class.simpleName, "Trying to find folder with id = ${folderId.value}")
        return folders.find { it.id == folderId }
    }

    override fun createFolder(name: String): Folder {
        val newId = Folder.ID((folders.maxByOrNull { it.id.value }?.id?.value ?: 0) + 1)
        val newFolder = Folder(newId, name)
        folders.add(newFolder)
        Log.i(this::class.simpleName, "Createw new folder $newFolder")
        saveToPreferences()
        return newFolder
    }

    override fun setFolderName(folderId: Folder.ID, folderName: String) {
        val index = this.folders.indexOfFirst { it.id == folderId }
        this.folders[index] = this.folders[index].copy(name = folderName)
        saveToPreferences()
    }

    override fun deleteFolder(folderId: Folder.ID): Boolean {
        val deleted = folders.removeIf { it.id == folderId }
        saveToPreferences()
        return deleted
    }
}