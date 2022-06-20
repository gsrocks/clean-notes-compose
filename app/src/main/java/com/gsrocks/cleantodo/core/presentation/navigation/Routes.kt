package com.gsrocks.cleantodo.core.presentation.navigation

sealed class Screen(val route: String) {
    object NotesScreen : Screen("notes")

    object AddEditNoteScreen : Screen("add-edit-note") {
        const val argumentNoteId = "noteId"
        const val argumentNoteColor = "noteColor"
    }
}
