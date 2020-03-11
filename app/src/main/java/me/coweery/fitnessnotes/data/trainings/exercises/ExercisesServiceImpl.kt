package me.coweery.fitnessnotes.data.trainings.exercises

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.zipWith
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ExercisesServiceImpl @Inject constructor(
    private val exercisesDAO: ExercisesDAO
) : ExercisesService {

    override fun create(exercise: Exercise): Single<Exercise> {

        return exercisesDAO.insert(exercise)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .zipWith(Single.just(exercise)) { id, ex -> ex.copy(id = id) }
    }

    override fun getByTrainingId(id: Long): Single<List<Exercise>> {

        return exercisesDAO.getByTrainingId(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}