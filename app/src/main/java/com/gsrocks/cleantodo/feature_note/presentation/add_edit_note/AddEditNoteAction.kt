package com.gsrocks.cleantodo.feature_note.presentation.add_edit_note

sealed class AddEditNoteAction {
    data class TitleEntered(val title: String) : AddEditNoteAction()

    data class ContentEntered(val content: String) : AddEditNoteAction()

    data class ChangeColor(val color: Int) : AddEditNoteAction()

    object SaveNote : AddEditNoteAction()
}
