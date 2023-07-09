package com.example.calendar_screen.domain

import android.util.Log
import com.example.database.NoteEntity
import com.example.database.NoteRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class NotesByDayUseCase(
    private val noteRepository: NoteRepo,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) {
    suspend operator fun invoke(day:Int, month: Int, year: Int): List<NoteEntity>{
        return noteRepository.findNotesByDay(day, month, year)
    }
}
