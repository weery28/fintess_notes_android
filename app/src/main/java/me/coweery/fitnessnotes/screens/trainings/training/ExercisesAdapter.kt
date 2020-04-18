package me.coweery.fitnessnotes.screens.trainings.training

import me.coweery.fitnessnotes.data.trainings.exercises.Exercise
import me.coweery.fitnessnotes.data.trainings.exercises.sets.Set

interface ExercisesAdapter {

    fun add(exercise : Exercise)

    fun add(set : Set)

    fun deleteExercise(id : Long)

    fun deleteSet(id : Long)

}