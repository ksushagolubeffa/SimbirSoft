package com.example.database.di

import android.content.Context
import androidx.room.Room
import com.example.database.NoteDao
import com.example.database.NoteDatabase
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    @Provides
    fun provideAppDatabase(context: Context): NoteDatabase {
        return Room.databaseBuilder(context, NoteDatabase::class.java, DATABASE_NAME)
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    fun provideNoteDao(database: NoteDatabase): NoteDao {
        return database.getNoteDao()
    }

    companion object {
        private const val DATABASE_NAME = "notes.db"
    }
}
