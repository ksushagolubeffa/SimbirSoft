package com.example.calendar_screen.data

import android.util.Log
import com.example.database.Note
import com.example.database.NoteDao
import com.example.database.NoteEntity
import com.example.database.NoteRepo
import java.sql.Timestamp
import java.util.Calendar

class NoteRepositoryImpl(private val noteDao: NoteDao): NoteRepo {

    override suspend fun deleteNote(id: Int) {
        noteDao.delete(id)
    }

    override suspend fun addNote(dateStart: Timestamp,
                                 dateFinish: Timestamp,
                                 name: String,
                                 description: String) {
        Log.e("RepoImpl", "${dateStart} ${dateFinish} ${name} ${description}")
        val note = Note(0, dateStart, dateFinish, name, description)
        noteDao.add(note)
    }

    override suspend fun updateNote(id: Int,
                                    dateStart: Timestamp,
                                    dateFinish: Timestamp,
                                    name: String,
                                    description: String) {
        val note = Note(id, dateStart, dateFinish, name, description)
        noteDao.update(note)
    }

    override suspend fun getAll(): List<NoteEntity> {
        val result = ArrayList<NoteEntity>()
        val notes = noteDao.getAll()
        notes.forEach {
            result.add(
                getNote(it)
            )
        }
//        Log.e("GetAll", result.toString())
        return result
    }

    override suspend fun findNoteById(id: Int): NoteEntity {
        val note = noteDao.getOne(id)
        return getNote(note!!)
    }

    override suspend fun findNotesByMonth(month: Int, year: Int): List<NoteEntity> {
        val result = ArrayList<NoteEntity>()
        val notes = getAll()
        notes.forEach {
            if ((it.dateStart[1] == month && it.dateStart[0] == year) || (it.dateFinish[1] == month && it.dateFinish[0] == year)) {
                result.add(it)
            }
        }
        return result
    }

    override suspend fun findNotesByDay(day: Int, month: Int, year: Int): List<NoteEntity> {
        val result = ArrayList<NoteEntity>()
        val notes = getAll()
        Log.e("RepoImpl ByDay get All", notes.toString())
        notes.forEach {
            Log.e("Abs", (kotlin.math.abs(it.dateStart[2] - it.dateFinish[2]) < 2).toString())
            if(kotlin.math.abs(it.dateStart[2] - it.dateFinish[2]) < 2) {
                if ((it.dateStart[1] == month && it.dateStart[0] == year && it.dateStart[2] == day) || (it.dateFinish[1] == month && it.dateFinish[0] == year && it.dateFinish[2] == day)) {
                    Log.e("RepositoryImpl", "${it.dateStart} ${day}")
                    Log.e("RepositoryImpl", "${it.dateFinish} ${day}")
                    result.add(it)
                }
            }else{
                val helper = ArrayList<Int>()
                helper.add(it.dateFinish[2])
                for (number in it.dateStart[2]  until it.dateFinish[2]) {
                    helper.add(number)
                }
                if ((it.dateStart[1] == month && it.dateStart[0] == year && helper.contains(day)) || (it.dateFinish[1] == month && it.dateFinish[0] == year && helper.contains(day))) {
                    result.add(it)
                }
            }
        }
        Log.e("Result days", result.toString())
        return result
    }

    private fun dateParser(timestamp: Timestamp): List<Int> {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp.time

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val hours = calendar.get(Calendar.HOUR_OF_DAY)
        val minutes = calendar.get(Calendar.MINUTE)
        val seconds = calendar.get(Calendar.SECOND)
        return listOf(year, month, day, hours, minutes, seconds)
    }

    private fun getNote(note: Note): NoteEntity {
        val start = dateParser(note.dateStart)
        val finish = dateParser(note.dateFinish)
        return NoteEntity(
            note.id,
            note.name,
            note.description,
            start,
            finish
        )
    }
}
