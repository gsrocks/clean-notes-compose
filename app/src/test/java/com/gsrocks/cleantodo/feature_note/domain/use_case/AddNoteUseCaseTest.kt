package com.gsrocks.cleantodo.feature_note.domain.use_case

import com.google.common.truth.Truth.assertThat
import com.gsrocks.cleantodo.feature_note.data.repository.FakeNoteRepository
import com.gsrocks.cleantodo.feature_note.domain.model.Note
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.random.Random

class AddNoteUseCaseTest {

    private lateinit var addNoteUseCase: AddNoteUseCase
    private lateinit var fakeNoteRepository: FakeNoteRepository

    @Before
    fun setup() {
        fakeNoteRepository = FakeNoteRepository()
        addNoteUseCase = AddNoteUseCase(fakeNoteRepository)
    }

    @Test
    fun `Insert note with blank title, failure result`() = runBlocking {
        val note = Note(title = " ", content = " ", timestamp = 1, color = 1)
        val result = addNoteUseCase(note)

        assertThat(result.exceptionOrNull()?.message).isEqualTo("Title of the note can't be empty")
    }

    @Test
    fun `Insert note, successfully inserted`() = runBlocking {
        val id = Random.nextInt()
        val note = Note(id = id, title = "Note", content = "Content", timestamp = 1, color = 1)
        val result = addNoteUseCase(note)

        assertThat(result.isSuccess).isTrue()

        val noteFromRepository = fakeNoteRepository.getNoteById(id)

        assertThat(noteFromRepository.getOrNull()?.id).isEqualTo(id)
    }
}
