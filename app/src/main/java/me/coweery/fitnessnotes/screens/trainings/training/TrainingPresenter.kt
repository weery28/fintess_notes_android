package me.coweery.fitnessnotes.screens.trainings.training

import io.reactivex.Single
import io.reactivex.rxkotlin.zipWith
import me.coweery.fitnessnotes.data.exercises.Exercise
import me.coweery.fitnessnotes.data.exercises.ExercisesService
import me.coweery.fitnessnotes.data.trainings.Training
import me.coweery.fitnessnotes.data.trainings.TrainingsService
import me.coweery.fitnessnotes.screens.BasePresenter
import java.util.*
import javax.inject.Inject

class TrainingPresenter @Inject constructor(
    private val exercisesService: ExercisesService,
    private val trainingsService: TrainingsService
) : BasePresenter<TrainingContract.View>(),
    TrainingContract.Presenter {

    private var mTraining: Training? = null

    override fun onAddExercisesClicked() {
        view?.showExerciseInput()
    }

    override fun onExercisesDataReceived(name: String, weight: Float, count: Int, sets: Int) {

        if (mTraining == null) {
            with(Training(null, "Без имени ${Date()}", false)) {
                trainingsService.save(this)
                    .zipWith(Single.just(this)) { id, training ->
                        training.copy(id = id)
                    }
            }

        } else {
            Single.just(mTraining!!)
        }
            .flatMap {
                mTraining = it
                exercisesService.create(
                    Exercise(null, name, it.id!!, weight, count, sets)
                )
            }
            .safetySubscribe(
                {
                    view?.addExercise(it)
                },
                {
                    it.printStackTrace()
                }
            )
    }

    override fun onEditingDone() {
        view?.showPreparedTrainingScreen()
    }

    override fun onTrainingReceived(trainingId: Long) {

        trainingsService.get(trainingId)
            .flatMap { training ->
                exercisesService.getByTrainingId(training.id!!).map {
                    training to it
                }
            }
            .safetySubscribe(
                { (training, exercises) ->
                    mTraining = training
                    exercises.forEach {
                        view?.addExercise(it)
                    }

                },
                {
                    it.printStackTrace()
                }
            )
    }

    override fun onEditClicked() {
        view?.showEditScreen()
    }
}