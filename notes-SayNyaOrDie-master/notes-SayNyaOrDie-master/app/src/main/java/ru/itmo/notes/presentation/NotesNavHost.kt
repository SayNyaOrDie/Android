package ru.itmo.notes.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.itmo.notes.domain.entities.Folder
import ru.itmo.notes.domain.entities.Note
import ru.itmo.notes.presentation.folders.FolderScreen
import ru.itmo.notes.presentation.folders.FoldersScreen
import ru.itmo.notes.presentation.notes.NoteScreen
import ru.itmo.notes.presentation.trashbin.TrashBinScreen

@Composable
fun NotesNavHost(
    modifier: Modifier = Modifier,
    viewModel: NotesViewModel,
    navController: NavHostController = rememberNavController(),
    startDestination: String = NotesDestinations.FOLDERS_ROUTE,
    navActions: NotesNavigationActions = remember(navController) {
        NotesNavigationActions(navController)
    }
) {
    var folders = viewModel.folders
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    )
    {
        composable(NotesDestinations.FOLDERS_ROUTE) {
            FoldersScreen(
                folders = folders,
                onCreateFolder = viewModel::createNewFolder,
                onNavigateToFolderById = navActions::navigateToFolder,
                onNavigateToTrashBin = navActions::navigateToTrashBin,
                onDeleteFolder = viewModel::deleteFolder,
            )
        }
        composable(
            NotesDestinations.FOLDER_ROUTE,
            arguments = listOf(navArgument(NotesDestinationsArgs.FOLDER_ID_ARG) {
                type = NavType.LongType
            })
        ) {
            val folderId =
                Folder.ID(it.arguments!!.getLong(NotesDestinationsArgs.FOLDER_ID_ARG))
            var folder by remember {
                mutableStateOf(
                    viewModel.getFolder(folderId)!!
                )
            }
            var notes by remember {
                mutableStateOf(
                    viewModel.getNotesForFolder(folderId)!!
                )
            }
            FolderScreen(
                folder,
                notes,
                onAddNewNote = {
                    viewModel.addNewNote(folderId)
                    notes = viewModel.getNotesForFolder(folderId)!!
                },
                onNavigateToNote = {
                    navActions.navigateToNote(folderId, it)
                },
                onMoveNoteToTrashBin = {
                    viewModel.moveNoteToTrashBin(folderId, it)
                    notes = viewModel.getNotesForFolder(folderId)!!
                },
                onUpdateFolderName = {
                    viewModel.updateFolderName(folderId, it)
                    folder = viewModel.getFolder(folderId)!!
                }
            )
        }

        composable(
            NotesDestinations.NOTE_ROUTE,
            arguments = listOf(
                navArgument(NotesDestinationsArgs.FOLDER_ID_ARG) { type = NavType.LongType },
                navArgument(NotesDestinationsArgs.NOTE_ID_ARG) { type = NavType.LongType },
            )
        ) {
            val folderId =
                Folder.ID(it.arguments!!.getLong(NotesDestinationsArgs.FOLDER_ID_ARG))
            val noteId = Note.ID(it.arguments!!.getLong(NotesDestinationsArgs.NOTE_ID_ARG))
            val note = viewModel.getNote(folderId, noteId)!!
            NoteScreen(
                note
            ) { viewModel.setNoteContent(folderId, noteId, it) }
        }

        composable(NotesDestinations.TRASH_BIN_ROUTE) {
            var deletedNotes by remember {
                mutableStateOf(viewModel.getDeletedNotes())
            }
            TrashBinScreen(deletedNotes) { folderId, noteId ->
                viewModel.restoreNote(folderId, noteId)
                deletedNotes = viewModel.getDeletedNotes()
            }
        }
    }
}