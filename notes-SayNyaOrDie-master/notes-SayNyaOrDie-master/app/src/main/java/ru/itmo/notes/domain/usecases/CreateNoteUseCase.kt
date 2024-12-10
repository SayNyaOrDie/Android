package ru.itmo.notes.domain.usecases

import ru.itmo.notes.domain.entities.Folder
import ru.itmo.notes.domain.entities.Note
import ru.itmo.notes.domain.repositories.NotesRepository

class CreateNoteUseCase(private val notesRepository: NotesRepository) {
    fun createNote(folderId: Folder.ID): Note = notesRepository.createNewNote(folderId)
}