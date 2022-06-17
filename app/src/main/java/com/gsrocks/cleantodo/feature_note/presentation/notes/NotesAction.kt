package com.gsrocks.cleantodo.feature_note.presentation.notes

import com.gsrocks.cleantodo.feature_note.domain.model.Note
import com.gsrocks.cleantodo.feature_note.domain.util.NoteOrder

sealed class NotesAction {
    data class Order(val noteOrder: NoteOrder) : NotesAction()

    data class DeleteNote(val note: Note) : NotesAction()

    object RestoreNote : NotesAction()

    object ToggleOrderSection : NotesAction()
}
