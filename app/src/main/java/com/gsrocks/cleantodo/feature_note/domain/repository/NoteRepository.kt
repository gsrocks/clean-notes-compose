package com.gsrocks.cleantodo.feature_note.domain.repository

import com.gsrocks.cleantodo.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun watchNotes(): Flow<List<Note>>

    suspend fun getNoteById(id: Int): Result<Note?>

    suspend fun insertNote(note: Note): Result<Unit>

    suspend fun deleteNote(note: Note): Result<Unit>
}