package com.gsrocks.cleantodo.feature_note.presentation.add_edit_note

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gsrocks.cleantodo.R
import com.gsrocks.cleantodo.core.presentation.components.ClearFocusView
import com.gsrocks.cleantodo.core.presentation.navigation.UiEvent
import com.gsrocks.cleantodo.core.presentation.utils.LocalSpacing
import com.gsrocks.cleantodo.feature_note.domain.model.Note
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun AddEditNoteScreen(
    onNavigateUp: () -> Unit,
    noteColor: Int,
    viewModel: AddEditNoteViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current

    val noteBackgroundAnimatable = remember {
        Animatable(
            Color(if (noteColor != -1) noteColor else viewModel.noteColor)
        )
    }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val (titleFocusRequester, contentFocusRequester) = FocusRequester.createRefs()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> snackbarHostState.showSnackbar(
                    message = event.message.asString(
                        context
                    )
                )
                is UiEvent.NavigateUp -> onNavigateUp()
                else -> Unit
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onAction(AddEditNoteAction.SaveNote) }
            ) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = stringResource(R.string.save_note)
                )
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        ClearFocusView {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(noteBackgroundAnimatable.value)
                    .padding(padding)
                    .padding(spacing.spaceMedium)
                    .padding(bottom = FloatingActionButtonDefaults.LargeIconSize + spacing.spaceMedium)
            ) {
                ColorSelectSection(
                    noteColor = viewModel.noteColor,
                    onSelect = { colorInt ->
                        scope.launch {
                            noteBackgroundAnimatable.animateTo(
                                targetValue = Color(colorInt),
                                animationSpec = tween(durationMillis = 500)
                            )
                        }
                        viewModel.onAction(AddEditNoteAction.ChangeColor(colorInt))
                    }
                )
                Spacer(modifier = Modifier.height(spacing.spaceMedium))
                TextField(
                    value = viewModel.noteTitle,
                    onValueChange = {
                        viewModel.onAction(AddEditNoteAction.TitleEntered(it))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(titleFocusRequester)
                        .focusProperties { next = contentFocusRequester },
                    placeholder = { Text(text = stringResource(R.string.enter_title)) },
                    singleLine = true,
                    textStyle = MaterialTheme.typography.headlineSmall,
                    colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent),
                    keyboardActions = KeyboardActions(
                        onDone = { contentFocusRequester.requestFocus() }
                    )
                )
                Spacer(modifier = Modifier.height(spacing.spaceMedium))
                TextField(
                    value = viewModel.noteContent,
                    onValueChange = {
                        viewModel.onAction(AddEditNoteAction.ContentEntered(it))
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .focusRequester(contentFocusRequester),
                    placeholder = { Text(text = stringResource(R.string.enter_some_content)) },
                    textStyle = MaterialTheme.typography.bodyMedium,
                    colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent)
                )
            }
        }
    }
}

@Composable
fun ColorSelectSection(
    noteColor: Int,
    onSelect: (Int) -> Unit
) {
    val spacing = LocalSpacing.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.spaceSmall),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Note.noteColors.forEach { color ->
            val colorInt = color.toArgb()
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .shadow(spacing.spaceMedium, CircleShape)
                    .clip(CircleShape)
                    .background(color)
                    .border(
                        width = 3.dp,
                        color = when (noteColor) {
                            colorInt -> Color.Black
                            else -> Color.Transparent
                        },
                        shape = CircleShape
                    )
                    .clickable { onSelect(colorInt) }
            )
        }
    }
}