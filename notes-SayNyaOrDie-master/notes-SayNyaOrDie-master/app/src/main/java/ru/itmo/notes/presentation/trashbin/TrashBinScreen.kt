package ru.itmo.notes.presentation.trashbin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.itmo.notes.domain.entities.Folder
import ru.itmo.notes.domain.entities.Note

@Composable
fun TrashBinScreen(deletedNotes: List<Note>, onRestoreNote: (Folder.ID, Note.ID) -> Unit) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(text = "Корзина", fontSize = 24.sp, color = MaterialTheme.colorScheme.primary)
        TrashBinList(notes = deletedNotes, onNoteClick = onRestoreNote)
    }
}

@Preview
@Composable
fun TrashBinScreenPreview() {
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
    TrashBinScreen(notes, { a, b -> })
}