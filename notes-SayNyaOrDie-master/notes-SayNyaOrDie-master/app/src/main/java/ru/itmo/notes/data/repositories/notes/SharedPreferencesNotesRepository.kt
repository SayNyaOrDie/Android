package ru.itmo.notes.data.repositories.notes

import android.content.SharedPreferences
import android.util.Log
import ru.itmo.notes.domain.entities.Folder
import ru.itmo.notes.domain.entities.Note
import ru.itmo.notes.domain.repositories.NotesRepository
import ru.itmo.notes.util.deserializers.NoteDeserializer
import ru.itmo.notes.util.serializers.NoteSerializer

class SharedPreferencesNotesRepository(private val sharedPreferences: SharedPreferences) :
    NotesRepository {
    private fun saveToPreferences() {
        val editor = this.sharedPreferences.edit()
        val set = this.notes
            .flatMap { it.value }
            .map { NoteSerializer.serialize(it) }
            .toSet()
        editor.putStringSet("notes", set)
        editor.apply()
        Log.i(this::class.simpleName, "Saved ${set.size} notes to SharedPreferences")
    }

    private fun readFromPreferences() {
        val notes = this.sharedPreferences.getStringSet("notes", emptySet())!!
            .map { NoteDeserializer.deserialize(it)!! }
            .groupBy { it.folderId }
            .mapValues { ArrayList(it.value) }
            .toMap()
        this.notes.clear()
        this.notes.putAll(notes)
        Log.i(this::class.simpleName, "Loaded ${this.notes.size} notes from SharedPreferences")
    }

    val notes: HashMap<Folder.ID, ArrayList<Note>> = hashMapOf()

    init {
        readFromPreferences()
    }

    override fun getNotesForFolder(folderId: Folder.ID): List<Note> =
        notes[folderId]?.filter { !it.isInTrashBin } ?: emptyList()

    override fun getNoteById(folderId: Folder.ID, noteId: Note.ID): Note? {
        return notes[folderId]?.find { it.id == noteId && !it.isInTrashBin }
    }

    override fun createNewNote(folderId: Folder.ID): Note {
        val currentNotes = notes.getOrDefault(folderId, emptyList())
        val newNoteId = Note.ID((notes.getOrDefault(folderId, emptyList())
            .maxByOrNull { it.id.value }
            ?.id?.value ?: 0) + 1)
        val newNote = Note(newNoteId, folderId, content = "")
        val newNotesList = ArrayList(currentNotes)
        newNotesList.add(newNote)
        notes[folderId] = newNotesList
        saveToPreferences()
        return newNote
    }

    override fun editNoteContent(folderId: Folder.ID, noteId: Note.ID, newContent: String) {
        val oldNoteIndex = getNoteIndexById(folderId, noteId)!!
        val newNote = notes[folderId]!![oldNoteIndex].copy(content = newContent)
        notes[folderId]!![oldNoteIndex] = newNote
        saveToPreferences()
    }

    override fun moveNoteToTrashBin(folderId: Folder.ID, noteId: Note.ID): Boolean {
        val noteIndex = getNoteIndexById(folderId, noteId)!!
        val oldNote = notes[folderId]!![noteIndex]
        if (oldNote.isInTrashBin) {
            return false
        }
        notes[folderId]!![noteIndex] = oldNote.copy(isInTrashBin = true)
        saveToPreferences()
        return true
    }

    override fun getDeletedNotes(): List<Note> {
        return notes.flatMap { it.value }.filter { it.isInTrashBin }
    }

    override fun restoreNote(folderId: Folder.ID, noteId: Note.ID): Boolean {
        Log.i(this::class.qualifiedName, "Restoring note ${folderId.value}/${noteId.value}")
        val noteIndex = getDeletedNoteIndexById(folderId, noteId)!!
        val oldNote = notes[folderId]!![noteIndex]
        if (!oldNote.isInTrashBin) {
            return false
        }
        notes[folderId]!![noteIndex] = oldNote.copy(isInTrashBin = false)
        saveToPreferences()
        return true
    }

    override fun deleteNotesByFolder(folderId: Folder.ID): Int {
        val notesToDelete = notes[folderId]?.size ?: -1;
        notes.remove(folderId)
        saveToPreferences()
        return notesToDelete
    }

    private fun getNoteIndexById(folderId: Folder.ID, noteId: Note.ID): Int? =
        notes[folderId]?.indexOfFirst { it.id == noteId && !it.isInTrashBin }

    private fun getDeletedNoteIndexById(folderId: Folder.ID, noteId: Note.ID): Int? =
        notes[folderId]?.indexOfFirst { it.id == noteId && it.isInTrashBin }
}