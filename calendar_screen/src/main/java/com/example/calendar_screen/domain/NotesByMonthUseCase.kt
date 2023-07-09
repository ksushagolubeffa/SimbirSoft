package com.example.calendar_screen.domain

import com.example.database.NoteEntity
import com.example.database.NoteRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class NotesByMonthUseCase(
    private val noteRepository: NoteRepo,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) {
    suspend operator fun invoke(month: Int, year: Int): List<NoteEntity>{
        return noteRepository.findNotesByMonth(month, year)
    }
}
