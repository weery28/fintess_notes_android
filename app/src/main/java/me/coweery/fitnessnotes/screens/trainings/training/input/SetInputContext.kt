package me.coweery.fitnessnotes.screens.trainings.training.input

import java.io.Serializable

data class SetInputContext(
    val id: Long?,
    val exerciseId: Long,
    val repsCount: Int?,
    val weight: Float?,
    val index: Int
) : Serializable