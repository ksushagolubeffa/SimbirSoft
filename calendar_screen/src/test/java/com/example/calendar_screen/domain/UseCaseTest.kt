package com.example.calendar_screen.domain

import com.example.database.NoteEntity
import com.example.database.NoteRepo
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFailsWith

@OptIn(ExperimentalCoroutinesApi::class)
class UseCaseTest {

    @MockK
    lateinit var noteRepository: NoteRepo

    private lateinit var getNoteByIdUseCase: NoteUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getNoteByIdUseCase = NoteUseCase(noteRepository)
    }

    @Test
    fun whenGetNoteByIdUseCaseExpectedSuccess() {
        val requestId = 0
        val expectedData: NoteEntity = mockk {
            every { id } returns 37
            every { name } returns "cinema"
            every { description } returns "go to the cinema"
            every { dateStart } returns listOf(2023, 7, 13, 13, 20, 0)
            every { dateFinish } returns listOf(2023, 7, 15, 15, 20, 0)

        }
        coEvery {
            noteRepository.findNoteById(requestId)
        } returns expectedData
        runTest {
            val result = getNoteByIdUseCase.invoke(id = requestId)
            assertEquals(expectedData, result)
        }
    }

    @Test
    fun whenGetNoteByIdUseCaseExpectedError() {
        val requestId = 170
        coEvery {
            noteRepository.findNoteById(requestId)
        } throws RuntimeException()
        runTest {
            assertFailsWith<RuntimeException> {
                getNoteByIdUseCase.invoke(id = requestId)
            }
        }
    }

}
