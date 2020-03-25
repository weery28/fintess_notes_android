package me.coweery.fitnessnotes.data.trainings

import androidx.room.Embedded
import androidx.room.Relation
import me.coweery.fitnessnotes.data.trainings.exercises.Exercise
import me.coweery.fitnessnotes.data.trainings.exercises.sets.Set

class FullTraining {

    @Embedded
    lateinit var training : Training

    @Relation(parentColumn = "id", entity = Exercise::class, entityColumn = "trainingId")
    lateinit var exercises : List<Exercise>

    @Relation(parentColumn = "id", entity = Set::class, entityColumn = "exerciseId")
    lateinit var sets : List<Set>
}