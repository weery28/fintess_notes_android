package me.coweery.fitnessnotes.screens.trainings.training

import io.reactivex.Single
import io.reactivex.rxkotlin.zipWith
import me.coweery.fitnessnotes.data.trainings.exercises.Exercise
import me.coweery.fitnessnotes.data.trainings.exercises.ExercisesService
import me.coweery.fitnessnotes.data.trainings.Training
import me.coweery.fitnessnotes.data.trainings.TrainingsService
import me.coweery.fitnessnotes.data.trainings.exercises.sets.Set
import me.coweery.fitnessnotes.data.trainings.exercises.sets.SetsService
import me.coweery.fitnessnotes.screens.BasePresenter
import java.util.*
import javax.inject.Inject

class TrainingPresenter @Inject constructor(
    private val exercisesService: ExercisesService,
    private val trainingsService: TrainingsService,
    private val setsService: SetsService
) : BasePresenter<TrainingContract.View>(),
    TrainingContract.Presenter {

    private var mTraining: Training? = null
    private var state : TrainingState = TrainingState.STOPPED

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

    override fun onStartTrainingClicked() {
        if (state == TrainingState.ACTIVE){
            state = TrainingState.STOPPED
            view?.showStoppedTrainingScreen()
        } else {
            state = TrainingState.ACTIVE
            view?.showActiveTrainingScreen()
        }

    }

    override fun onExerciseDeleteClicked(exercise: Exercise) {

        exercisesService.delete(exercise.id!!)
            .safetySubscribe(
                {
                    view?.deleteExercise(exercise.id)
                },
                {

                })
    }

    override fun onSetClicked(exercise: Exercise, setIndex: Int) {

        view?.showSetInput(exercise.weight, exercise.count){
            weight, count ->
            setsService.create(Set(
                null,
                exercise.id!!,
                count,
                weight,
                setIndex
            ))
                .safetySubscribe(
                    {
                        view?.addSet(it)
                    },
                    {
                        it.printStackTrace()
                    }
                )
        }
    }
}