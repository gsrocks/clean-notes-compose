package com.gsrocks.cleantodo.feature_note.domain.use_case

import com.google.common.truth.Truth.assertThat
import com.gsrocks.cleantodo.feature_note.data.repository.FakeNoteRepository
import com.gsrocks.cleantodo.feature_note.domain.model.Note
import com.gsrocks.cleantodo.feature_note.domain.util.NoteOrder
import com.gsrocks.cleantodo.feature_note.domain.util.OrderType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class WatchNotesUseCaseTest {

    private lateinit var watchNotesUseCase: WatchNotesUseCase
    private lateinit var fakeNoteRepository: FakeNoteRepository

    @Before
    fun setup() {
        fakeNoteRepository = FakeNoteRepository()
        watchNotesUseCase = WatchNotesUseCase(fakeNoteRepository)

        val initialNotes = ('a'..'z').mapIndexed { index, c ->
            Note(
                id = index,
                title = c.toString(),
                content = c.toString(),
                timestamp = index.toLong(),
                color = index
            )
        }.shuffled()

        runBlocking {
            initialNotes.forEach {
                fakeNoteRepository.insertNote(it)
            }
        }
    }

    @Test
    fun `Order notes by title ascending, correct order`() = runBlocking {
        val notes = watchNotesUseCase(NoteOrder.Title(OrderType.Ascending)).first()
        for (i in 0..notes.size - 2) {
            assertThat(notes[i].title).isLessThan(notes[i + 1].title)
        }
    }

    @Test
    fun `Order notes by title descending, correct order`() = runBlocking {
        val notes = watchNotesUseCase(NoteOrder.Title(OrderType.Descending)).first()
        for (i in 0..notes.size - 2) {
            assertThat(notes[i].title).isGreaterThan(notes[i + 1].title)
        }
    }

    @Test
    fun `Order notes by date ascending, correct order`() = runBlocking {
        val notes = watchNotesUseCase(NoteOrder.Date(OrderType.Ascending)).first()
        for (i in 0..notes.size - 2) {
            assertThat(notes[i].timestamp).isLessThan(notes[i + 1].timestamp)
        }
    }

    @Test
    fun `Order notes by date descending, correct order`() = runBlocking {
        val notes = watchNotesUseCase(NoteOrder.Date(OrderType.Descending)).first()
        for (i in 0..notes.size - 2) {
            assertThat(notes[i].timestamp).isGreaterThan(notes[i + 1].timestamp)
        }
    }

    @Test
    fun `Order notes by color ascending, correct order`() = runBlocking {
        val notes = watchNotesUseCase(NoteOrder.Color(OrderType.Ascending)).first()
        for (i in 0..notes.size - 2) {
            assertThat(notes[i].color).isLessThan(notes[i + 1].color)
        }
    }

    @Test
    fun `Order notes by color descending, correct order`() = runBlocking {
        val notes = watchNotesUseCase(NoteOrder.Color(OrderType.Descending)).first()
        for (i in 0..notes.size - 2) {
            assertThat(notes[i].color).isGreaterThan(notes[i + 1].color)
        }
    }
}
