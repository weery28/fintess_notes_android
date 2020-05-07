package me.coweery.fitnessnotes.data.trainings.exercises

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

interface ExercisesService {

    fun create(exercise: Exercise): Single<Exercise>

    fun getByTrainingId(id: Long): Single<List<Exercise>>

    fun getFullByTrainingId(id: Long): Single<List<ExerciseWithSets>>

    fun delete(id: Long): Completable

    fun update(exercise: Exercise): Completable

    fun updateAll(exercises: List<Exercise>): Completable

    fun getLastCompletion(exerciseName: String, exceptTrainingId: Long?): Maybe<ExerciseCompletion>

    fun getExecrciseCount(trainingId: Long): Single<Int>
}