package me.coweery.fitnessnotes.data.trainings

import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.coweery.fitnessnotes.data.trainings.exercises.ExercisesDAO
import me.coweery.fitnessnotes.data.trainings.exercises.sets.SetsDAO
import javax.inject.Inject

class TrainingsServiceImpl @Inject constructor(
    private val trainingsDAO: TrainingsDAO,
    private val exercisesDAO: ExercisesDAO,
    private val setsDAO: SetsDAO
) : TrainingsService {

    override fun getAll(): Single<List<Training>> {

        return trainingsDAO.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun save(training: Training): Single<Long> {
        return trainingsDAO
            .insert(training)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun get(id: Long): Single<Training> {

        return trainingsDAO.get(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .toSingle()
    }

    override fun getFullTraining(id: Long): Maybe<TrainingWithExercises> {

        return trainingsDAO.getFullTraining(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}