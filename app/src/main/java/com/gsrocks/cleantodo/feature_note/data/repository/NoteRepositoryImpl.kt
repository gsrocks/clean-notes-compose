package com.gsrocks.cleantodo.feature_note.data.repository

import com.gsrocks.cleantodo.feature_note.data.data_source.NoteDao
import com.gsrocks.cleantodo.feature_note.data.entity.NoteEntity
import com.gsrocks.cleantodo.feature_note.domain.model.Note
import com.gsrocks.cleantodo.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepositoryImpl(
    private val dao: NoteDao
) : NoteRepository {
    override fun watchNotes(): Flow<List<Note>> {
        return dao.watchNotes().map { it.map { noteEntity -> noteEntity.toNote() } }
    }

    override suspend fun getNoteById(id: Int): Result<Note> {
        return try {
            val result = dao.getNoteById(id).toNote()
            Result.success(result)
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }

    override suspend fun insertNote(note: Note): Result<Unit> {
        return try {
            dao.insertNote(NoteEntity.fromNote(note))
            Result.success(Unit)
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }

    override suspend fun deleteNote(note: Note): Result<Unit> {
        return try {
            dao.deleteNote(NoteEntity.fromNote(note))
            Result.success(Unit)
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }
}