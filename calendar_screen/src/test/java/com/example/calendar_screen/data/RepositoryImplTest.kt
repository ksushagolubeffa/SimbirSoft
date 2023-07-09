package com.example.calendar_screen.data

import com.example.database.Note
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import kotlinx.coroutines.test.runTest
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import com.example.database.NoteDao
import com.example.database.NoteEntity
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import java.sql.Timestamp
import kotlin.test.assertFailsWith

@OptIn(ExperimentalCoroutinesApi::class)
class RepositoryImplTest {

    @MockK
    lateinit var dao: NoteDao

    private lateinit var noteRepositoryImpl: NoteRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        noteRepositoryImpl = NoteRepositoryImpl(dao)
    }

    private val expectedNoteResponse = mockk<Note> {
        every { id } returns 37
        every { name } returns "cinema"
        every { description } returns "go to the cinema"
        every { dateStart } returns Timestamp(2023 - 1900, 7 - 1, 13, 13, 20, 0, 0)
        every { dateFinish } returns Timestamp(2023 - 1900, 7 - 1, 15, 15, 20, 0, 0)
    }

    @Test
    fun whenGetNoteByIdExpectedSuccess() {
        val requestId = 37
        val expectedResult = NoteEntity(
            37,
            "cinema",
            "go to the cinema",
            listOf(2023, 7, 13, 13, 20, 0),
            listOf(2023, 7, 15, 15, 20, 0)
        )
        coEvery {
            dao.getOne(requestId)
        } returns expectedNoteResponse

        runTest {
            val result = noteRepositoryImpl.findNoteById(requestId)
            assertEquals(expectedResult, result)
        }
    }

    @Test
    fun whenGetWeatherByIdExpectedError() {
        val requestId = 170
        coEvery {
            dao.getOne(requestId)
        } throws Throwable()
        runTest {
            assertFailsWith<Throwable> {
                noteRepositoryImpl.findNoteById(requestId)
            }
        }
    }
}
