package ru.itmo.notes.domain.usecases

import ru.itmo.notes.domain.entities.Folder
import ru.itmo.notes.domain.entities.Note
import ru.itmo.notes.domain.repositories.NotesRepository

open class GetNotesUseCase(private val notesRepository: NotesRepository) {
    fun getNotesForFolder(folderId: Folder.ID): List<Note>? =
        notesRepository.getNotesForFolder(folderId)

    fun getNoteById(folderId: Folder.ID, noteId: Note.ID): Note? =
        notesRepository.getNoteById(folderId, noteId)

    fun getDeletedNotes(): List<Note> = notesRepository.getDeletedNotes()
}