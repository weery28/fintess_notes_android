package me.coweery.fitnessnotes.data.trainings.exercises

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.zipWith
import io.reactivex.schedulers.Schedulers
import me.coweery.fitnessnotes.data.trainings.TrainingsDAO
import me.coweery.fitnessnotes.data.trainings.exercises.sets.SetsService
import javax.inject.Inject

class ExercisesServiceImpl @Inject constructor(
    private val exercisesDAO: ExercisesDAO,
    private val setsService: SetsService,
    private val trainingsDAO: TrainingsDAO
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

    override fun delete(id: Long): Completable {

        return exercisesDAO.delete(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun update(exercise: Exercise): Completable {

        return exercisesDAO.update(exercise)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun updateAll(exercises: List<Exercise>): Completable {
        return exercisesDAO.update(*exercises.toTypedArray())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getLastCompletion(
        exerciseName: String,
        exceptTrainingId: Long?
    ): Maybe<ExerciseCompletion> {

        return (exceptTrainingId?.let { trainingsDAO.getLastWithExercise(exerciseName, it) }
            ?: trainingsDAO.getLastWithExercise(exerciseName))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap {
                exercisesDAO
                    .getByTrainingIdAndName(it.id!!, exerciseName)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map { exercise ->
                        it to exercise
                    }
            }
            .flatMapSingleElement { (training, exercise) ->
                setsService.getByExerciseId(exercise.id!!)
                    .map { sets ->
                        ExerciseCompletion(
                            exercise,
                            sets,
                            training.date
                        )
                    }
            }
    }
}