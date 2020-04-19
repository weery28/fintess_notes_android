package me.coweery.fitnessnotes.data.trainings

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import me.coweery.fitnessnotes.data.DateConverter
import java.util.*

@Entity
@TypeConverters(DateConverter::class)
data class Training(
    @PrimaryKey
    val id: Long? = null,
    val name: String,
    val isComplete: Boolean,
    val creationDate: Date,
    val date: Date,
    val isSynced: Boolean = false,
    val serverId: Long? = null
)