package com.gsrocks.cleantodo.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gsrocks.cleantodo.feature_note.data.data_source.NoteDao
import com.gsrocks.cleantodo.feature_note.data.entity.NoteEntity

@Database(
    entities = [NoteEntity::class],
    version = 1
)
abstract class NoteDatabase : RoomDatabase() {

    abstract val noteDao: NoteDao

    companion object {
        const val DATABASE_NAME = "notes_db"
    }
}