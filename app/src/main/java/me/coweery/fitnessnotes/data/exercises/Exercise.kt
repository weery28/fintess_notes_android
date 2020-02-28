package me.coweery.fitnessnotes.data.exercises

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import me.coweery.fitnessnotes.data.trainings.Training

@Entity(
    foreignKeys = arrayOf(
        ForeignKey(
            entity = Training::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("trainingId"),
            onDelete = ForeignKey.CASCADE
        )
    ),
    indices = arrayOf(Index("trainingId"))
)
data class Exercise(
    @PrimaryKey
    val id: Long,
    val name: String,
    val trainingId: Long
)