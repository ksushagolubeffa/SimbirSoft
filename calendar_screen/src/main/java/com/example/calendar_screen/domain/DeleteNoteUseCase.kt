package com.example.calendar_screen.domain

import com.example.database.NoteRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteNoteUseCase(
    private val noteRepository: NoteRepo,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(
        id: Int
    ) {
        withContext(dispatcher) {
            noteRepository.deleteNote(id)
        }
    }
}
