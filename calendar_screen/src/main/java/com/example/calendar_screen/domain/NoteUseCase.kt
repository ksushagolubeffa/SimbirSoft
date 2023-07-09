package com.example.calendar_screen.domain

import com.example.database.NoteEntity
import com.example.database.NoteRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class NoteUseCase(
    private val noteRepository: NoteRepo
) {
    suspend operator fun invoke(id: Int): NoteEntity{
        return noteRepository.findNoteById(id)
    }
}
