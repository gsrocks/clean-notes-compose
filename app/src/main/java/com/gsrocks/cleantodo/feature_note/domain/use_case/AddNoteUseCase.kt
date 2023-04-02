package com.gsrocks.cleantodo.feature_note.domain.use_case

import com.gsrocks.cleantodo.feature_note.domain.model.Note
import com.gsrocks.cleantodo.feature_note.domain.repository.NoteRepository

class AddNoteUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(note: Note): Result<Unit> {
        if (note.title.isBlank()) {
            return Result.failure(Exception("Title of the note can't be empty"))
        }
        return repository.insertNote(note)
    }
}