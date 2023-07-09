package com.example.calendar_screen.domain

import android.util.Log
import com.example.database.NoteRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.sql.Timestamp

class UpdateNoteUseCase(
    private val noteRepository: NoteRepo,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(
        id: Int,
        dateStart: Timestamp,
        dateFinish: Timestamp,
        name: String,
        description: String
    ) {
        withContext(dispatcher) {
            Log.e("update usecase", "try to update")
            noteRepository.updateNote(id, dateStart, dateFinish, name, description)
        }
    }
}
