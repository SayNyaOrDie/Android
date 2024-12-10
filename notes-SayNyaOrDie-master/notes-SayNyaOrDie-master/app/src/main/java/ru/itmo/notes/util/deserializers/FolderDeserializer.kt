package ru.itmo.notes.util.deserializers

import ru.itmo.notes.domain.entities.Folder

object FolderDeserializer : Deserializer<Folder> {
    private val reg = Regex("Folder[(]id=(\\d+),name=\"(.*)\"[)]")

    override fun deserialize(string: String): Folder? {
        val res = reg.find(string)?.groupValues
        if (res == null) {
            return null
        }
        val id = res[1].toLongOrNull() ?: return null
        val name = res[2] ?: return null
        return Folder(id = Folder.ID(id), name = name)
    }
}