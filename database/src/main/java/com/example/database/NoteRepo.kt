package com.example.database

import java.sql.Timestamp

interface NoteRepo {

    suspend fun deleteNote(id: Int)

    suspend fun addNote(dateStart: Timestamp,
                        dateFinish: Timestamp,
                        name: String,
                        description: String)

    suspend fun updateNote(id: Int,
                           dateStart: Timestamp,
                           dateFinish: Timestamp,
                           name: String,
                           description: String)

    suspend fun getAll(): List<NoteEntity>

    suspend fun findNoteById(id: Int): NoteEntity

    suspend fun findNotesByMonth(month: Int, year: Int): List<NoteEntity>

    suspend fun findNotesByDay(day: Int, month: Int, year: Int): List<NoteEntity>
}