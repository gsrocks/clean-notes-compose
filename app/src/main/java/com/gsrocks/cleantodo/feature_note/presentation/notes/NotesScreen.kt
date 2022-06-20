package com.gsrocks.cleantodo.feature_note.presentation.notes

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.gsrocks.cleantodo.R
import com.gsrocks.cleantodo.core.presentation.navigation.Screen
import com.gsrocks.cleantodo.core.presentation.utils.LocalSpacing
import com.gsrocks.cleantodo.core.presentation.navigation.UiEvent
import com.gsrocks.cleantodo.core.utils.TestTags
import com.gsrocks.cleantodo.feature_note.presentation.notes.components.NoteItem
import com.gsrocks.cleantodo.feature_note.presentation.notes.components.OrderSection
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: NotesViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current

    val state = viewModel.state
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onNavigate(UiEvent.Navigate(Screen.AddEditNoteScreen.route))
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_note)
                )
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(spacing.spaceMedium)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.your_notes),
                    style = MaterialTheme.typography.headlineMedium
                )
                IconButton(
                    onClick = {
                        viewModel.onAction(NotesAction.ToggleOrderSection)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Sort,
                        contentDescription = stringResource(R.string.sort)
                    )
                }
            }
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(
                    modifier = Modifier
                        .testTag(TestTags.ORDER_SECTION)
                        .fillMaxWidth()
                        .padding(spacing.spaceMedium),
                    noteOrder = state.noteOrder,
                    onOrderChange = {
                        viewModel.onAction(NotesAction.Order(it))
                    }
                )
            }
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.notes) {
                    NoteItem(
                        note = it,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                val argNoteId = Screen.AddEditNoteScreen.argumentNoteId
                                val argNoteColor = Screen.AddEditNoteScreen.argumentNoteColor
                                onNavigate(
                                    UiEvent.Navigate(
                                        Screen.AddEditNoteScreen.route +
                                                "?$argNoteId=${it.id}&$argNoteColor=${it.color}"
                                    )
                                )
                            },
                        onDeleteClick = {
                            viewModel.onAction(NotesAction.DeleteNote(it))
                            scope.launch {
                                val result = snackbarHostState.showSnackbar(
                                    message = context.getString(R.string.note_deleted),
                                    actionLabel = context.getString(R.string.undo)
                                )
                                if (result == SnackbarResult.ActionPerformed) {
                                    viewModel.onAction(NotesAction.RestoreNote)
                                }
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(spacing.spaceMedium))
                }
            }
        }
    }
}