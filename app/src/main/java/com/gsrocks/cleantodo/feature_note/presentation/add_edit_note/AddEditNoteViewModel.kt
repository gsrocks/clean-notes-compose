package com.gsrocks.cleantodo.feature_note.presentation.add_edit_note

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gsrocks.cleantodo.R
import com.gsrocks.cleantodo.core.presentation.navigation.UiEvent
import com.gsrocks.cleantodo.core.presentation.utils.UiText
import com.gsrocks.cleantodo.core.utils.empty
import com.gsrocks.cleantodo.feature_note.domain.model.Note
import com.gsrocks.cleantodo.feature_note.domain.use_case.AddNoteUseCase
import com.gsrocks.cleantodo.feature_note.domain.use_case.GetNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val getNoteUseCase: GetNoteUseCase,
    private val addNoteUseCase: AddNoteUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var noteTitle by mutableStateOf(String.empty)
        private set

    var noteContent by mutableStateOf(String.empty)
        private set

    var noteColor by mutableStateOf(Note.noteColors.random().toArgb())
        private set

    private var currentNoteId: Int? = null


    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if (noteId != -1) {
                viewModelScope.launch {
                    val result = getNoteUseCase(noteId)
                    if (result.isSuccess) {
                        result.getOrNull()?.also {
                            currentNoteId = it.id
                            noteTitle = it.title
                            noteContent = it.content
                            noteColor = it.color
                        }
                    }
                }
            }
        }
    }


    fun onAction(action: AddEditNoteAction) {
        when (action) {
            is AddEditNoteAction.TitleEntered -> {
                noteTitle = action.title
            }
            is AddEditNoteAction.ContentEntered -> {
                noteContent = action.content
            }
            is AddEditNoteAction.ChangeColor -> {
                noteColor = action.color
            }
            is AddEditNoteAction.SaveNote -> {
                viewModelScope.launch {
                    val result = addNoteUseCase(
                        Note(
                            id = currentNoteId,
                            title = noteTitle,
                            content = noteContent,
                            color = noteColor,
                            timestamp = System.currentTimeMillis()
                        )
                    )
                    if (result.isSuccess) {
                        _uiEvent.send(UiEvent.NavigateUp)
                    } else {
                        val errorMessage = result.exceptionOrNull()?.localizedMessage
                        _uiEvent.send(
                            UiEvent.ShowSnackbar(
                                if (errorMessage != null) {
                                    UiText.DynamicString(errorMessage)
                                } else {
                                    UiText.StringResource(R.string.could_not_save_note)
                                }
                            )
                        )
                    }
                }
            }
        }
    }
}