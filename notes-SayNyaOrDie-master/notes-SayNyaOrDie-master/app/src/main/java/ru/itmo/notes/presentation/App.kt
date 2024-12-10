package ru.itmo.notes.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.itmo.notes.ui.theme.NotesTheme

@Composable
fun App(viewModel: NotesViewModel) {
    NotesTheme {
        Surface {
            Scaffold { paddingValues ->
                NotesNavHost(
                    viewModel = viewModel,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}