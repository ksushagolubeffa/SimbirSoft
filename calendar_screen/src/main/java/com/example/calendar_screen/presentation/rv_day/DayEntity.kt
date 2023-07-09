package com.example.calendar_screen.presentation.rv_day

import com.example.database.NoteEntity

data class DayEntity (
    val time: String,
    var list: ArrayList<NoteEntity>?,
    val timeStart: Int
)