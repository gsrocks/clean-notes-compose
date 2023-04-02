package com.gsrocks.cleantodo.feature_note.domain.use_case

import com.gsrocks.cleantodo.feature_note.domain.model.Note
import com.gsrocks.cleantodo.feature_note.domain.repository.NoteRepository

class GetNoteUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(id: Int): Result<Note> {
        return repository.getNoteById(id)
    }
}