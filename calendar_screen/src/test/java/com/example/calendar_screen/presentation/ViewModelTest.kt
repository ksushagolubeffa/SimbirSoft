package com.example.calendar_screen.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.calendar_screen.domain.AddNoteUseCase
import com.example.calendar_screen.domain.DeleteNoteUseCase
import com.example.calendar_screen.domain.NoteUseCase
import com.example.calendar_screen.domain.NotesByDayUseCase
import com.example.calendar_screen.domain.UpdateNoteUseCase
import com.example.calendar_screen.presentation.utils.MainDispatcherRule
import com.example.calendar_screen.presentation.viewmodel.NoteViewModel
import com.example.database.NoteEntity
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

@OptIn(ExperimentalCoroutinesApi::class)
class ViewModelTest {

    @MockK
    lateinit var notesByDayUseCase: NotesByDayUseCase

    @MockK
    lateinit var noteUseCase: NoteUseCase

    @MockK
    lateinit var addNoteUseCase: AddNoteUseCase

    @MockK
    lateinit var updateNoteUseCase: UpdateNoteUseCase

    @MockK
    lateinit var deleteNoteUseCase: DeleteNoteUseCase

    private lateinit var noteViewModel: NoteViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        noteViewModel = NoteViewModel(
            notesByDayUseCase,
            noteUseCase,
            addNoteUseCase,
            updateNoteUseCase,
            deleteNoteUseCase
        )
    }

    @Test
    fun whenLoadNote() {
        val expectedNoteInfo = mockk<NoteEntity> {
            every { id } returns 37
            every { name } returns "cinema"
            every { description } returns "go to the cinema"
            every { dateStart } returns listOf(2023, 7, 13, 13, 20, 0)
            every { dateFinish } returns listOf(2023, 7, 15, 15, 20, 0)
        }
        coEvery {
            noteUseCase.invoke(id = 37)
        } returns expectedNoteInfo

        noteViewModel.getNoteById(37)
        val result: Result<NoteEntity> = Result.success(expectedNoteInfo)

        // assert
        assertEquals(result, noteViewModel.note.value)
    }

}
