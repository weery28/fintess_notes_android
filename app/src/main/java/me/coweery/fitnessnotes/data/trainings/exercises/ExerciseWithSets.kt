package me.coweery.fitnessnotes.data.trainings.exercises

import androidx.room.Embedded
import androidx.room.Relation
import me.coweery.fitnessnotes.data.trainings.exercises.sets.Set

class ExerciseWithSets(

    @Embedded
    val exercise: Exercise,

    @Relation(parentColumn = "id", entityColumn = "exerciseId", entity = Set::class)
    val sets: List<Set>
)