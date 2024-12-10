package ru.itmo.notes.presentation.folders

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.itmo.notes.domain.entities.Folder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FoldersListItem(
    folder: Folder,
    onClickFolder: () -> Unit,
    onDeleteFolder: () -> Unit,
    showDeleteButton: Boolean,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier, onClick = onClickFolder) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = folder.name,
                modifier = Modifier.padding(8.dp),
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.weight(1.0f))
            if (showDeleteButton) {
                IconButton(
                    onClick = onDeleteFolder,
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.error)
                        .fillMaxHeight(1.0f)
                ) {
                    Icon(
                        Icons.Default.Delete,
                        "Delete",
                        modifier = Modifier.size(32.dp),
                        tint = MaterialTheme.colorScheme.onError
                    )
                }
            }
        }
    }
}

@Composable
fun FoldersList(
    folders: List<Folder>,
    onClickFolder: (folderId: Folder.ID) -> Unit,
    onDeleteFolder: (folderId: Folder.ID) -> Unit,
    onNavigateToTrashBin: () -> Unit,
    modifier: Modifier = Modifier

) {
    if (folders.isEmpty()) {
        TrashBinFolder(
            modifier = Modifier
                .padding(vertical = 4.dp, horizontal = 16.dp),
            onNavigateToTrashBin
        )
    } else {
        LazyColumn {
            folders.map {
                item {
                    FoldersListItem(
                        folder = it,
                        onClickFolder = { onClickFolder(it.id) },
                        onDeleteFolder = { onDeleteFolder(it.id) },
                        showDeleteButton = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp, horizontal = 16.dp)
                    )
                }
            }
            item {
                TrashBinFolder(
                    modifier = Modifier
                        .padding(vertical = 4.dp, horizontal = 16.dp),
                    onNavigateToTrashBin
                )
            }
        }
    }
}

@Composable
fun TrashBinFolder(modifier: Modifier = Modifier, onNavigateToTrashBin: () -> Unit = {}) {
    FoldersListItem(
        folder = Folder(Folder.ID(-1), "Корзина"),
        onClickFolder = onNavigateToTrashBin,
        onDeleteFolder = {},
        showDeleteButton = false,
        modifier = modifier
    )
}

@Preview
@Composable
internal fun FoldersListPreview() {
    val folders = listOf(
        Folder(Folder.ID(1), "Test 1"),
        Folder(Folder.ID(2), "Test 2"),
        Folder(Folder.ID(3), "Test 3"),
    )
    FoldersList(folders = folders, {}, {}, {})
}

@Preview
@Composable
internal fun EmptyFoldersListPreview() {
    FoldersList(folders = emptyList(), {}, {}, {})
}