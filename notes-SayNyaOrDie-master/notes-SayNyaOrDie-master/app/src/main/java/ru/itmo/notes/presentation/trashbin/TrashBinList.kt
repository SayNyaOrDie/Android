package ru.itmo.notes.presentation.trashbin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.itmo.notes.domain.entities.Folder
import ru.itmo.notes.domain.entities.Note
import ru.itmo.notes.presentation.notes.NoteContent
import ru.itmo.notes.presentation.notes.NoteHeader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TrashBinListItem(note: Note, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(modifier = modifier, onClick = onClick) {
        Column(modifier = Modifier.padding(8.dp)) {
            NoteHeader(note = note)
            NoteContent(note = note)
        }
    }
}

@Composable
fun TrashBinList(notes: List<Note>, onNoteClick: (folderId: Folder.ID, noteId: Note.ID) -> Unit) {
    LazyColumn {
        notes.map {
            item {
                TrashBinListItem(
                    note = it,
                    modifier = Modifier
                        .fillMaxWidth(1.0f)
                        .padding(vertical = 4.dp)
                ) {
                    onNoteClick(it.folderId, it.id)
                }
            }
        }
    }
}

@Composable
@Preview
internal fun TrashBinListPreview() {
    val notes = listOf<Note>(
        Note(
            id = Note.ID(1),
            folderId = Folder.ID(1),
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
            true,
        ),
        Note(
            id = Note.ID(2),
            folderId = Folder.ID(1),
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque sit amet nunc semper, rutrum nibh ac, tincidunt ante. Nulla finibus dapibus iaculis. Etiam nec sollicitudin odio.",
            true,
        ),
        Note(
            id = Note.ID(3),
            folderId = Folder.ID(1),
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque sit amet nunc semper, rutrum nibh ac, tincidunt ante. Nulla finibus dapibus iaculis. Etiam nec sollicitudin odio. Aenean id faucibus est, eu efficitur massa. Ut arcu justo, consequat sed sapien sed, condimentum rhoncus nisi. Praesent fermentum erat at rutrum efficitur. Nulla facilisi. Nam ac turpis nisl. Curabitur augue lectus, viverra eget erat id, rutrum consectetur erat.",
            true,
        ),
    )
    TrashBinList(notes = notes, onNoteClick = { _, _ -> })
}