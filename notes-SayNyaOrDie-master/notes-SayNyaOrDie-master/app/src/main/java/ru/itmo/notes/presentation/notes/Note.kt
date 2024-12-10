package ru.itmo.notes.presentation.notes

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import ru.itmo.notes.domain.entities.Note

val NOTE_CONTENT_SIZE = 16.sp;
val NOTE_HEADER_SIZE = NOTE_CONTENT_SIZE * 1.5;

@Composable
fun NoteHeader(note: Note, modifier: Modifier = Modifier) {
    Text(
        "Заметка #${note.id.value} из папки #${note.folderId.value}",
        fontSize = NOTE_HEADER_SIZE,
        fontWeight = FontWeight.Bold,
        modifier = modifier,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1
    )
}

@Composable
fun NoteEditableContent(
    content: String,
    onEditContent: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = content, onValueChange = onEditContent,
        label = {
            Text("Текст заметки")
        },
        placeholder = {
            Text(text = "Введите текст заметки")
        },
        textStyle = TextStyle(fontSize = NOTE_CONTENT_SIZE),
        minLines = 3,
        modifier = modifier,
    )
}

@Composable
fun NoteContent(note: Note, modifier: Modifier = Modifier) {
    val presentationContent = note.content.ifBlank {
        "Заметка пустая"
    }
    Text(
        presentationContent,
        maxLines = 3,
        overflow = TextOverflow.Ellipsis,
        fontSize = NOTE_CONTENT_SIZE,
        modifier = modifier
    )
}