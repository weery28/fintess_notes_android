package me.coweery.fitnessnotes.screens.trainings.training.input

import java.io.Serializable

data class ExerciseInputContext (
    val id: Long?,
    val name: String?,
    val trainingId: Long?,
    val weight : Float?,
    val count : Int?,
    val sets : Int?
) : Serializable