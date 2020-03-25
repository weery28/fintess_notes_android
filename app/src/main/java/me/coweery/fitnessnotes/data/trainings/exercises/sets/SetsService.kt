package me.coweery.fitnessnotes.data.trainings.exercises.sets

import io.reactivex.Single

interface SetsService {

    fun createOrUpdate(set: Set): Single<Set>

    fun getByExerciseId(exerciseId : Long) : Single<List<Set>>
}