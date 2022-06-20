package com.gsrocks.cleantodo.feature_note.data.repository

import com.gsrocks.cleantodo.feature_note.domain.model.Note
import com.gsrocks.cleantodo.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeNoteRepository : NoteRepository {

    private val notes = mutableListOf<Note>()

    override fun watchNotes(): Flow<List<Note>> {
        return flowOf(notes)
    }

    override suspend fun getNoteById(id: Int): Result<Note> {
        return try {
            val note = notes.single { it.id == id }
            Result.success(note)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun insertNote(note: Note): Result<Unit> {
        return try {
            notes.add(note)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteNote(note: Note): Result<Unit> {
        return try {
            notes.remove(note)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}