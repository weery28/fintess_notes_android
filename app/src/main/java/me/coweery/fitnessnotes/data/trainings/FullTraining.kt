package me.coweery.fitnessnotes.data.trainings

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import me.coweery.fitnessnotes.data.trainings.exercises.Exercise
import me.coweery.fitnessnotes.data.trainings.exercises.sets.Set

class FullTraining (
    val training : Training,
    val exercises : List<Exercise>,
    val sets : List<Set>
)