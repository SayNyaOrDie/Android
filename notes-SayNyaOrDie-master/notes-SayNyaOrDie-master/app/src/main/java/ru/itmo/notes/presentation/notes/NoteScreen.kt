package ru.itmo.notes.presentation.notes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.itmo.notes.domain.entities.Folder
import ru.itmo.notes.domain.entities.Note

@Composable
fun NoteScreen(
    note: Note,
    onSaveNoteContent: (String) -> Unit,
) {
    var newNoteText by remember { mutableStateOf(note.content) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        NoteHeader(note = note)
        NoteEditableContent(content = newNoteText, onEditContent = {
            newNoteText = it
        }, modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp))
        Button(onClick = {
            onSaveNoteContent(newNoteText)
        }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Сохранить")
        }
    }
}

@Preview
@Composable
fun NoteScreenPreview() {
    val note = Note(Note.ID(1), Folder.ID(1), "Some cool note text")
    NoteScreen(note = note, onSaveNoteContent = {})
}