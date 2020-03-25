package me.coweery.fitnessnotes.screens.trainings.training

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.zipWith
import me.coweery.fitnessnotes.data.trainings.exercises.Exercise
import me.coweery.fitnessnotes.data.trainings.exercises.ExercisesService
import me.coweery.fitnessnotes.data.trainings.Training
import me.coweery.fitnessnotes.data.trainings.TrainingsService
import me.coweery.fitnessnotes.data.trainings.exercises.sets.Set
import me.coweery.fitnessnotes.data.trainings.exercises.sets.SetsService
import me.coweery.fitnessnotes.screens.BasePresenter
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class TrainingPresenter @Inject constructor(
    private val exercisesService: ExercisesService,
    private val trainingsService: TrainingsService,
    private val setsService: SetsService
) : BasePresenter<TrainingContract.View>(),
    TrainingContract.Presenter {

    private var mTraining: Training? =null

    private val simpleDateFormat = SimpleDateFormat("dd MMM YY HH:mm")

    override fun onAddExercisesClicked() {
        view?.showExerciseInput(
            Exercise(null, null, null, null, null, null)
        )
    }

    override fun onExercisesDataReceived(exercise: Exercise) {

        if (mTraining == null) {
            with(Training(null, "Тренировка от ${simpleDateFormat.format(Date())}", false)) {
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
                if (exercise.id == null){
                    exercisesService.create(exercise.copy(trainingId = it.id!!))
                } else {
                    exercisesService.update(exercise)
                        .andThen(Single.just(exercise))
                }
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

        trainingsService.getFullTraining(trainingId)
            .safetySubscribe(
                {
                    mTraining = it.training
                    it.exercises.forEach {
                        view?.addExercise(it)
                    }
                    it.sets.forEach {
                        view?.addSet(it)
                    }
                },
                {
                    it.printStackTrace()
                }
            )
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

    override fun onExerciseEditClicked(exercise: Exercise) {
        view?.showExerciseInput(exercise)
    }

    override fun onSetClicked(exercise: Exercise, set: Set?, setIndex : Int) {

        view?.showSetInput(
            set ?: Set(null, exercise.id!!, exercise.count!!, exercise.weight!!, setIndex)
        )
    }

    override fun onSetDataReceived(set: Set) {

        setsService.createOrUpdate(set)
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