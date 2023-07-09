package com.example.database

import java.sql.Timestamp

data class NoteEntity (
    val id: Int,
    val name: String,
    val description: String,
    val dateStart: List<Int>,
    val dateFinish: List<Int>
)
