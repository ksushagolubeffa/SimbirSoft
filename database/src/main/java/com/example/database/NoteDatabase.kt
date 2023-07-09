package com.example.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Note::class], version = 1, exportSchema = true)
abstract class NoteDatabase:RoomDatabase() {
    abstract fun getNoteDao(): NoteDao
}