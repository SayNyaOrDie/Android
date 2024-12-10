package ru.itmo.notes.presentation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import ru.itmo.notes.domain.entities.Folder
import ru.itmo.notes.domain.entities.Note
import ru.itmo.notes.presentation.NotesDestinations.FOLDERS_ROUTE
import ru.itmo.notes.presentation.NotesDestinations.TRASH_BIN_ROUTE
import ru.itmo.notes.presentation.NotesDestinationsArgs.FOLDER_ID_ARG
import ru.itmo.notes.presentation.NotesDestinationsArgs.NOTE_ID_ARG
import ru.itmo.notes.presentation.NotesScreens.FOLDERS_SCREEN
import ru.itmo.notes.presentation.NotesScreens.TRASH_BIN_SCREEN

private object NotesScreens {
    const val FOLDERS_SCREEN = "folders"
    const val TRASH_BIN_SCREEN = "trashbin"
}

object NotesDestinationsArgs {
    const val FOLDER_ID_ARG = "folderId"
    const val NOTE_ID_ARG = "noteId"
}

object NotesDestinations {
    const val FOLDERS_ROUTE = FOLDERS_SCREEN
    const val FOLDER_ROUTE = "$FOLDERS_SCREEN/{$FOLDER_ID_ARG}"
    const val NOTE_ROUTE = "$FOLDERS_SCREEN/{$FOLDER_ID_ARG}/{$NOTE_ID_ARG}"
    const val TRASH_BIN_ROUTE = TRASH_BIN_SCREEN
}

class NotesNavigationActions(private val navController: NavHostController) {
    fun navigateToFolders() {
        navController.navigate(FOLDERS_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateToFolder(id: Folder.ID) {
        navController.navigate("${FOLDERS_SCREEN}/${id.value}")
    }

    fun navigateToNote(folderId: Folder.ID, noteId: Note.ID) {
        navController.navigate("${FOLDERS_SCREEN}/${folderId.value}/${noteId.value}")
    }

    fun navigateToTrashBin() {
        navController.navigate(TRASH_BIN_ROUTE)
    }
}
