package com.example.calendar_screen.domain

import android.util.Log
import com.example.database.NoteRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.sql.Timestamp

class AddNoteUseCase(
    private val noteRepository: NoteRepo,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(
        dateStart: Timestamp,
        dateFinish: Timestamp,
        name: String,
        description: String
    ) {
        withContext(dispatcher) {
            noteRepository.addNote(dateStart, dateFinish, name, description)
        }
    }
}