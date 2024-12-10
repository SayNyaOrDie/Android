package ru.itmo.notes.domain.repositories

import ru.itmo.notes.domain.entities.Folder
import ru.itmo.notes.domain.entities.Note

interface NotesRepository {
    /**
     * Returns notes for folder with given ID.
     * If no such folder exists, it returns `null`.
     * It will ignore notes in trash bin.
     */
    fun getNotesForFolder(folderId: Folder.ID): List<Note>?

    /**
     * Gets note with given ID.
     * Returns `null` if note with such ID does not exist.
     * It will ignore notes in trash bin.
     */
    fun getNoteById(folderId: Folder.ID, noteId: Note.ID): Note?

    fun createNewNote(folderId: Folder.ID): Note

    fun editNoteContent(folderId: Folder.ID, noteId: Note.ID, newContent: String)

    /**
     * Moves note to trash bin.
     * Returns `true` if note was successfully moved.
     * Returns `false` if such not does not exists or it is already
     * in trash bin.
     */
    fun moveNoteToTrashBin(folderId: Folder.ID, noteId: Note.ID): Boolean

    fun getDeletedNotes(): List<Note>

    fun restoreNote(folderId: Folder.ID, noteId: Note.ID): Boolean

    /**
     * Permanently deletes all notes from given folder.
     * Returns number of notes deleted
     */
    fun deleteNotesByFolder(folderId: Folder.ID): Int
}