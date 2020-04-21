package me.coweery.fitnessnotes.screens.trainings.training

import me.coweery.fitnessnotes.data.trainings.exercises.Exercise
import me.coweery.fitnessnotes.data.trainings.exercises.ExerciseWithSets
import me.coweery.fitnessnotes.data.trainings.exercises.sets.Set

interface ExercisesAdapter {

    fun addToTail(exercise: ExerciseWithSets)

    fun update(exercise: ExerciseWithSets)

    fun deleteExercise(id: Long)
}