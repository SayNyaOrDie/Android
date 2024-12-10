package ru.itmo.notes.presentation.notes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.itmo.notes.domain.entities.Folder
import ru.itmo.notes.domain.entities.Note

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun NotesListItem(
    note: Note,
    onClickNote: () -> Unit,
    onDeleteNote: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier, onClick = onClickNote
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(0.8f)
            ) {
                NoteHeader(note = note)
                NoteContent(note = note)
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = onDeleteNote,
                modifier = Modifier.align(Alignment.CenterVertically),
            ) {
                Icon(
                    Icons.Filled.Delete,
                    "Delete",
                    Modifier.requiredSize(32.dp),
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
fun NotesList(
    notes: List<Note>,
    onClickNote: (Note.ID) -> Unit,
    onMoveNoteToTrashBin: (Note.ID) -> Unit
) {
    if (notes.isEmpty()) {
        Text(
            "Тут пока ничего нет...\nСоздайте заметку",
            fontSize = 24.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
    LazyColumn {
        item {
            notes.map {
                NotesListItem(
                    note = it,
                    onClickNote = { onClickNote(it.id) },
                    onDeleteNote = { onMoveNoteToTrashBin(it.id) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
            }
        }
    }

}

@Preview
@Composable
internal fun EmptyNotesListPreview() {
    val notes = listOf<Note>()
    NotesList(notes = notes, onClickNote = {}, onMoveNoteToTrashBin = {})
}

@Preview(showBackground = true)
@Composable
internal fun NotesListPreview() {
    val notes = listOf<Note>(
        Note(
            id = Note.ID(11111111111),
            folderId = Folder.ID(1),
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit."
        ),
        Note(
            id = Note.ID(2),
            folderId = Folder.ID(1),
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque sit amet nunc semper, rutrum nibh ac, tincidunt ante. Nulla finibus dapibus iaculis. Etiam nec sollicitudin odio."
        ),
        Note(
            id = Note.ID(3),
            folderId = Folder.ID(1),
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque sit amet nunc semper, rutrum nibh ac, tincidunt ante. Nulla finibus dapibus iaculis. Etiam nec sollicitudin odio. Aenean id faucibus est, eu efficitur massa. Ut arcu justo, consequat sed sapien sed, condimentum rhoncus nisi. Praesent fermentum erat at rutrum efficitur. Nulla facilisi. Nam ac turpis nisl. Curabitur augue lectus, viverra eget erat id, rutrum consectetur erat."
        ),
    )
    NotesList(notes = notes, onClickNote = {}, onMoveNoteToTrashBin = {})
}