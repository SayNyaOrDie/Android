package ru.itmo.notes.presentation.folders

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import ru.itmo.notes.domain.entities.Folder

class FoldersScreenViewModel() : ViewModel() {

}

@Composable
fun FoldersScreen(
    folders: List<Folder>,
    onCreateFolder: () -> Unit,
    onNavigateToFolderById: (folderId: Folder.ID) -> Unit,
    onNavigateToTrashBin: () -> Unit,
    onDeleteFolder: (folderId: Folder.ID) -> Unit,
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onCreateFolder) {
                Icon(Icons.Filled.Add, "Добавить папку")
            }
        }
    ) { paddingValues ->
        FoldersList(
            folders = folders,
            onClickFolder = onNavigateToFolderById,
            onNavigateToTrashBin = onNavigateToTrashBin,
            onDeleteFolder = onDeleteFolder,
            modifier = Modifier.padding(paddingValues)
        )
    }
}