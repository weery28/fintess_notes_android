package me.coweery.fitnessnotes.data

import androidx.room.TypeConverter
import java.util.*

class DateConverter {

    @TypeConverter
    fun toLong(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun toDate(time: Long): Date {
        return Date(time)
    }
}