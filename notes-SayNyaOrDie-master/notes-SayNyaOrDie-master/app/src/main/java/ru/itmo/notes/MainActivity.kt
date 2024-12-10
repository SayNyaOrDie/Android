package ru.itmo.notes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ru.itmo.notes.data.repositories.folders.SharedPreferencesFoldersRepository
import ru.itmo.notes.data.repositories.notes.SharedPreferencesNotesRepository
import ru.itmo.notes.domain.repositories.FoldersRepository
import ru.itmo.notes.domain.repositories.NotesRepository
import ru.itmo.notes.domain.usecases.CreateFolderUseCase
import ru.itmo.notes.domain.usecases.CreateNoteUseCase
import ru.itmo.notes.domain.usecases.DeleteFolderUseCase
import ru.itmo.notes.domain.usecases.EditFolderNameUseCase
import ru.itmo.notes.domain.usecases.EditNoteContentUseCase
import ru.itmo.notes.domain.usecases.GetFoldersUseCase
import ru.itmo.notes.domain.usecases.GetNotesUseCase
import ru.itmo.notes.domain.usecases.MoveNoteToTrashBinUseCase
import ru.itmo.notes.domain.usecases.RestoreNoteUseCase
import ru.itmo.notes.presentation.App
import ru.itmo.notes.presentation.NotesViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val preferences = this.getSharedPreferences("pref", MODE_PRIVATE)
        val foldersRepository: FoldersRepository = SharedPreferencesFoldersRepository(preferences)
        val notesRepository: NotesRepository = SharedPreferencesNotesRepository(preferences)

        val getFoldersUseCase = GetFoldersUseCase(foldersRepository)
        val getNotesUseCase = GetNotesUseCase(notesRepository)
        val createFoldersUseCase = CreateFolderUseCase(foldersRepository)
        val editFolderNameUseCase = EditFolderNameUseCase(foldersRepository)
        val editNoteContentUseCase = EditNoteContentUseCase(notesRepository)
        val createNoteUseCase = CreateNoteUseCase(notesRepository)
        val moveNoteToTrashBinUseCase = MoveNoteToTrashBinUseCase(notesRepository)
        val deleteFolderUseCase = DeleteFolderUseCase(foldersRepository, notesRepository)
        val restoreNoteUseCase = RestoreNoteUseCase(notesRepository)

        val viewModel = NotesViewModel(
            getFoldersUseCase = getFoldersUseCase,
            createFolderUseCase = createFoldersUseCase,
            getNotesUseCase = getNotesUseCase,
            createNoteUseCase = createNoteUseCase,
            moveNoteToTrashBinUseCase = moveNoteToTrashBinUseCase,
            editFolderNameUseCase = editFolderNameUseCase,
            editNoteContentUseCase = editNoteContentUseCase,
            deleteFolderUseCase = deleteFolderUseCase,
            restoreNoteUseCase = restoreNoteUseCase,
        )

        super.onCreate(savedInstanceState)
        setContent {
            App(viewModel)
        }
    }
}