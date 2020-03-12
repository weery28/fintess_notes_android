package me.coweery.fitnessnotes.data.trainings.exercises.sets

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import me.coweery.fitnessnotes.data.trainings.exercises.Exercise

@Entity(
    foreignKeys = arrayOf(
        ForeignKey(
            entity = Exercise::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("exerciseId"),
            onDelete = ForeignKey.CASCADE
        )
    ),
    indices = arrayOf(Index("exerciseId"))
)
data class Set(

    @PrimaryKey
    val id: Long? = null,
    val exerciseId: Long,
    val repsCount: Int,
    val weight: Float,
    val index : Int
)