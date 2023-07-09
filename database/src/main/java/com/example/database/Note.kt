package com.example.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.sql.Timestamp

@Entity(tableName = "note")
@TypeConverters(DateTimeConverter::class)
data class Note(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "date_start") var dateStart: Timestamp,
    @ColumnInfo(name = "date_finish") var dateFinish: Timestamp,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "description") var description: String,
)