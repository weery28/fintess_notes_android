package me.coweery.fitnessnotes.data.exercises

import io.reactivex.Single

interface ExercisesService {

    fun create(exercise: Exercise): Single<Exercise>

    fun getByTrainingId(id : Long) : Single<List<Exercise>>
}