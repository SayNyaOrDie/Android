package ru.itmo.notes.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import ru.itmo.notes.domain.entities.Folder
import ru.itmo.notes.domain.entities.Note
import ru.itmo.notes.domain.usecases.CreateFolderUseCase
import ru.itmo.notes.domain.usecases.CreateNoteUseCase
import ru.itmo.notes.domain.usecases.DeleteFolderUseCase
import ru.itmo.notes.domain.usecases.EditFolderNameUseCase
import ru.itmo.notes.domain.usecases.EditNoteContentUseCase
import ru.itmo.notes.domain.usecases.GetFoldersUseCase
import ru.itmo.notes.domain.usecases.GetNotesUseCase
import ru.itmo.notes.domain.usecases.MoveNoteToTrashBinUseCase
import ru.itmo.notes.domain.usecases.RestoreNoteUseCase

class NotesViewModel(
    private val getFoldersUseCase: GetFoldersUseCase,
    private val createFolderUseCase: CreateFolderUseCase,
    private val deleteFolderUseCase: DeleteFolderUseCase,
    private val getNotesUseCase: GetNotesUseCase,
    private val createNoteUseCase: CreateNoteUseCase,
    private val moveNoteToTrashBinUseCase: MoveNoteToTrashBinUseCase,
    private val editFolderNameUseCase: EditFolderNameUseCase,
    private val editNoteContentUseCase: EditNoteContentUseCase,
    private val restoreNoteUseCase: RestoreNoteUseCase,
) : ViewModel() {
    var folders by mutableStateOf(getFoldersUseCase.getFolders())
        private set;

    private fun updateFolders() {
        folders = getFoldersUseCase.getFolders();
    }

    fun deleteFolder(folderId: Folder.ID) {
        deleteFolderUseCase(folderId)
        updateFolders()
    }


    fun createNewFolder() {
        createFolderUseCase.createFolder("Новая папка")
        updateFolders()
    }

    fun getFolder(id: Folder.ID) = getFoldersUseCase.getFolder(id)

    fun getNotesForFolder(id: Folder.ID) = getNotesUseCase.getNotesForFolder(id)

    fun addNewNote(folderId: Folder.ID) {
        createNoteUseCase.createNote(folderId)
    }

    fun moveNoteToTrashBin(folderId: Folder.ID, noteId: Note.ID) {
        moveNoteToTrashBinUseCase.moveNoteToTrashBin(folderId, noteId)
    }

    fun updateFolderName(folderId: Folder.ID, newName: String) {
        editFolderNameUseCase.setFolderName(folderId, newName)
        updateFolders()
    }

    fun getNote(folderId: Folder.ID, noteId: Note.ID) =
        getNotesUseCase.getNoteById(folderId, noteId)

    fun setNoteContent(folderId: Folder.ID, noteId: Note.ID, content: String) {
        editNoteContentUseCase.invoke(folderId, noteId, content)
    }

    fun getDeletedNotes(): List<Note> = getNotesUseCase.getDeletedNotes()

    fun restoreNote(folderId: Folder.ID, noteId: Note.ID) =
        restoreNoteUseCase.restoreNote(folderId, noteId)
}