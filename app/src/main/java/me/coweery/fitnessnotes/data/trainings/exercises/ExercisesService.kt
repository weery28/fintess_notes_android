package me.coweery.fitnessnotes.data.trainings.exercises

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

interface ExercisesService {

    fun create(exercise: Exercise): Single<Exercise>

    fun getByTrainingId(id: Long): Single<List<Exercise>>

    fun delete(id: Long): Completable

    fun update(exercise: Exercise): Completable

    fun getLastCompletion(exerciseName: String, exceptTrainingId: Long?): Maybe<ExerciseCompletion>
}