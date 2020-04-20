package me.coweery.fitnessnotes.data.trainings

import androidx.room.Embedded
import androidx.room.Relation
import me.coweery.fitnessnotes.data.trainings.exercises.Exercise
import me.coweery.fitnessnotes.data.trainings.exercises.ExerciseWithSets

class TrainingWithExercises(

    @Embedded
    val training: Training,

    @Relation(parentColumn = "id", entityColumn = "trainingId", entity = Exercise::class)
    val exercises: List<ExerciseWithSets>
)