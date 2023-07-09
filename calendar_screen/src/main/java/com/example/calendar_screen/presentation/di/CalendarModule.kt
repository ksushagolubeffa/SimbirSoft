package com.example.calendar_screen.presentation.di

import android.content.Context
import com.example.calendar_screen.data.NoteRepositoryImpl
import com.example.calendar_screen.domain.AddNoteUseCase
import com.example.calendar_screen.domain.DeleteNoteUseCase
import com.example.calendar_screen.domain.NoteUseCase
import com.example.calendar_screen.domain.NotesByDayUseCase
import com.example.calendar_screen.domain.UpdateNoteUseCase
import com.example.database.NoteDao
import com.example.database.NoteRepo
import dagger.Module
import dagger.Provides

@Module
class CalendarModule {

    @Provides
    fun provideAddNoteUseCase(
        repository: NoteRepo
    ): AddNoteUseCase = AddNoteUseCase(repository)

    @Provides
    fun provideDeleteNoteUseCase(
        repository: NoteRepo
    ): DeleteNoteUseCase = DeleteNoteUseCase(repository)

    @Provides
    fun provideNotesByDayUseCase(
        repository: NoteRepo
    ): NotesByDayUseCase = NotesByDayUseCase(repository)

    @Provides
    fun provideNoteUseCase(
        repository: NoteRepo
    ): NoteUseCase = NoteUseCase(repository)

    @Provides
    fun provideUpdateNoteUseCase(
        repository: NoteRepo
    ): UpdateNoteUseCase = UpdateNoteUseCase(repository)

    @Provides
    fun provideNoteRepository(
        noteDao: NoteDao
    ): NoteRepo = NoteRepositoryImpl(noteDao)
}