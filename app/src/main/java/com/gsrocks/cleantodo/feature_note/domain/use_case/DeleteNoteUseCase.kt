package com.gsrocks.cleantodo.feature_note.domain.use_case

import com.gsrocks.cleantodo.feature_note.domain.model.Note
import com.gsrocks.cleantodo.feature_note.domain.repository.NoteRepository

class DeleteNoteUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(note: Note): Result<Unit> {
        return repository.deleteNote(note)
    }
}