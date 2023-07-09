package com.example.calendar_screen.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.calendar_screen.domain.AddNoteUseCase
import com.example.calendar_screen.domain.DeleteNoteUseCase
import com.example.calendar_screen.domain.NoteUseCase
import com.example.calendar_screen.domain.NotesByDayUseCase
import com.example.calendar_screen.domain.UpdateNoteUseCase
import com.example.database.NoteEntity
import kotlinx.coroutines.launch
import java.sql.Timestamp

class NoteViewModel(
    private val notesByDayUseCase: NotesByDayUseCase,
    private val noteUseCase: NoteUseCase,
    private val addNoteUseCase: AddNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
): ViewModel() {

    private val _notes = MutableLiveData<Result<List<NoteEntity>>>()
    val notes: LiveData<Result<List<NoteEntity>>>
        get() = _notes

    private val _note = MutableLiveData<Result<NoteEntity>>()
    val note: LiveData<Result<NoteEntity>>
        get() = _note

    private var _error: MutableLiveData<Exception> = MutableLiveData()

    fun getNotesByDay(day:Int, month: Int, year: Int){
        viewModelScope.launch {
            try {
                Log.e("ViewModel byDay", "${day} ${month} ${year}")
                val notes = notesByDayUseCase(day, month, year)
                Log.e("byDay", notes.toString())
                _notes.value = Result.success(notes)
            } catch (ex: Exception) {
                _notes.value = Result.failure(ex)
                _error.value = ex
                Log.e("error", ex.toString())
            }
        }
    }

    fun getNoteById(id: Int){
        viewModelScope.launch {
            try {
//                Log.e("check id", id.toString())
                val note = noteUseCase(id)
//                Log.e("byId", note.toString())
                _note.value = Result.success(note)
            } catch (ex: Exception) {
                _note.value = Result.failure(ex)
                _error.value = ex
                Log.e("error", ex.toString())
            }
        }
    }

    fun deleteNote(id: Int){
        viewModelScope.launch {
            try {
                deleteNoteUseCase(id)
            } catch (ex: Exception) {
                _note.value = Result.failure(ex)
                _error.value = ex
                Log.e("error", ex.toString())
            }
        }
    }

    fun addNote(dateStart: Timestamp, dateFinish: Timestamp, name: String, description: String){
        viewModelScope.launch {
            try {
                Log.e("View Model addNote", "${dateStart} ${dateFinish} ${name} ${description}")
                addNoteUseCase(dateStart, dateFinish, name, description)
            } catch (ex: Exception) {
                _note.value = Result.failure(ex)
                _error.value = ex
                Log.e("error", ex.toString())
            }
        }

    }

    fun updateNote(id: Int, dateStart: Timestamp, dateFinish: Timestamp, name: String, description: String){
        Log.e("update view model", "${id}, ${dateStart}, ${dateFinish}, ${name}, ${description}")
        viewModelScope.launch {
            try {
                updateNoteUseCase(id, dateStart, dateFinish, name, description)
                Log.e("updated", "all good")
            } catch (ex: Exception) {
                _note.value = Result.failure(ex)
                _error.value = ex
                Log.e("error", ex.toString())
            }
        }
    }

    companion object{
        fun provideFactory(
            notesByDayUseCase: NotesByDayUseCase,
            noteUseCase: NoteUseCase,
            addNoteUseCase: AddNoteUseCase,
            updateNoteUseCase: UpdateNoteUseCase,
            deleteNoteUseCase: DeleteNoteUseCase

        ): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                NoteViewModel(
                    notesByDayUseCase,
                    noteUseCase,
                    addNoteUseCase,
                    updateNoteUseCase,
                    deleteNoteUseCase
                )
            }
        }
    }
}
