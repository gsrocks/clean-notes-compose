package com.gsrocks.cleantodo.feature_note.presentation.notes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gsrocks.cleantodo.feature_note.domain.model.Note
import com.gsrocks.cleantodo.feature_note.domain.use_case.AddNoteUseCase
import com.gsrocks.cleantodo.feature_note.domain.use_case.DeleteNoteUseCase
import com.gsrocks.cleantodo.feature_note.domain.use_case.WatchNotesUseCase
import com.gsrocks.cleantodo.feature_note.domain.util.NoteOrder
import com.gsrocks.cleantodo.feature_note.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val watchNotesUseCase: WatchNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val addNoteUseCase: AddNoteUseCase
) : ViewModel() {

    var state by mutableStateOf(NotesState())
        private set

    private var lastDeletedNote: Note? = null

    private var watchNotesJob: Job? = null

    init {
        watchNotes(NoteOrder.Date(OrderType.Descending))
    }

    fun onAction(action: NotesAction) {
        // TODO: handle error cases (show snackbar)
        when (action) {
            is NotesAction.Order -> {
                if (state.noteOrder == action.noteOrder) {
                    return
                }
                watchNotes(action.noteOrder)
            }
            is NotesAction.DeleteNote -> {
                viewModelScope.launch {
                    deleteNoteUseCase(action.note)
                    lastDeletedNote = action.note
                }
            }
            is NotesAction.RestoreNote -> {
                viewModelScope.launch {
                    addNoteUseCase(lastDeletedNote ?: return@launch)
                    lastDeletedNote = null
                }
            }
            is NotesAction.ToggleOrderSection -> {
                state = state.copy(isOrderSectionVisible = state.isOrderSectionVisible)
            }
        }
    }

    private fun watchNotes(noteOrder: NoteOrder) {
        watchNotesJob?.cancel()
        watchNotesJob = watchNotesUseCase(noteOrder)
            .onEach { notes ->
                state = state.copy(notes = notes)
            }
            .launchIn(viewModelScope)
    }

    override fun onCleared() {
        watchNotesJob?.cancel()
        super.onCleared()
    }
}