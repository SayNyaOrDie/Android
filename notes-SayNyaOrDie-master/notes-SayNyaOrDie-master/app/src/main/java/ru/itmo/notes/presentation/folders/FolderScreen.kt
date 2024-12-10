package ru.itmo.notes.presentation.folders

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.itmo.notes.domain.entities.Folder
import ru.itmo.notes.domain.entities.Note
import ru.itmo.notes.presentation.notes.NotesList

@Composable
fun FolderScreen(
    folder: Folder,
    notes: List<Note>,
    onAddNewNote: () -> Unit,
    onUpdateFolderName: (String) -> Unit,
    onMoveNoteToTrashBin: (Note.ID) -> Unit,
    onNavigateToNote: (noteId: Note.ID) -> Unit,
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddNewNote) {
                Icon(Icons.Filled.Add, "Добавить заметку")
            }
        },
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(paddingValues)
        ) {
            TextField(
                value = folder.name,
                onValueChange = onUpdateFolderName,
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Название папки")
                }
            )
            NotesList(
                notes = notes,
                onClickNote = onNavigateToNote,
                onMoveNoteToTrashBin = onMoveNoteToTrashBin,
            )
        }
    }
}
