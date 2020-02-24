package me.coweery.fitnessnotes.data.trainings

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Training(
    @PrimaryKey
    val id : Long,
    val name : String,
    val isComplete : Boolean
)