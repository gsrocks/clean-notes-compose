package com.gsrocks.cleantodo.di

import android.app.Application
import androidx.room.Room
import com.gsrocks.cleantodo.core.data.database.NoteDatabase
import com.gsrocks.cleantodo.feature_note.data.repository.NoteRepositoryImpl
import com.gsrocks.cleantodo.feature_note.domain.repository.NoteRepository
import com.gsrocks.cleantodo.feature_note.domain.use_case.AddNoteUseCase
import com.gsrocks.cleantodo.feature_note.domain.use_case.DeleteNoteUseCase
import com.gsrocks.cleantodo.feature_note.domain.use_case.WatchNotesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(@ApplicationContext application: Application): NoteDatabase {
        return Room.databaseBuilder(
            application,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(database: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(database.noteDao)
    }

    @Provides
    @Singleton
    fun provideWatchNotesUseCase(noteRepository: NoteRepository): WatchNotesUseCase {
        return WatchNotesUseCase(noteRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteNoteUseCase(noteRepository: NoteRepository): DeleteNoteUseCase {
        return DeleteNoteUseCase(noteRepository)
    }

    @Provides
    @Singleton
    fun provideAddNoteUseCase(noteRepository: NoteRepository): AddNoteUseCase {
        return AddNoteUseCase(noteRepository)
    }
}