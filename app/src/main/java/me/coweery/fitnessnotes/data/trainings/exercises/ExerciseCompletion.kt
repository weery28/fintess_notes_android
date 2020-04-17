package me.coweery.fitnessnotes.data.trainings.exercises

import me.coweery.fitnessnotes.data.trainings.exercises.sets.Set
import java.util.*

class ExerciseCompletion(
    val exercise: Exercise,
    val sets: List<Set>,
    val date: Date
)