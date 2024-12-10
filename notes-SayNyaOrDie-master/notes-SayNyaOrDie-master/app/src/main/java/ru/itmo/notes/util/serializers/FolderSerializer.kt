package ru.itmo.notes.util.serializers

import ru.itmo.notes.domain.entities.Folder

object FolderSerializer : Serializer<Folder> {
    override fun serialize(input: Folder): String {
        return "Folder(id=${input.id.value},name=\"${input.name}\")"
    }
}