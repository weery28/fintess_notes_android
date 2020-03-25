package me.coweery.fitnessnotes.data.trainings.exercises

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import me.coweery.fitnessnotes.data.trainings.Training
import java.io.Serializable

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
    val id: Long? = null,
    val name: String?,
    val trainingId: Long?,
    val weight : Float?,
    val count : Int?,
    val sets : Int?
) : Serializable