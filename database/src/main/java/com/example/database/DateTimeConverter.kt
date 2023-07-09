package com.example.database

import androidx.room.TypeConverter
import java.sql.Timestamp
import java.util.Date

class DateTimeConverter {

    @TypeConverter
    fun toTimestamp(timestamp: Long?): Timestamp? =
        if (timestamp == null) null
        else Timestamp(timestamp)

    @TypeConverter
    fun fromTimestamp(timestamp: Timestamp?): Long? =
        timestamp?.time
}
