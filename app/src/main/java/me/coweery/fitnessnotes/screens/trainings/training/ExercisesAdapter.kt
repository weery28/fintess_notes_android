package me.coweery.fitnessnotes.screens.trainings.training

import me.coweery.fitnessnotes.data.trainings.exercises.Exercise
import me.coweery.fitnessnotes.data.trainings.exercises.sets.Set

interface ExercisesAdapter {

    fun update(exercise: Exercise)

    fun update(set: Set)

    fun deleteSet(setId: Long)

    fun deleteExercise(id: Long)

    fun actualizeIndexes()
}