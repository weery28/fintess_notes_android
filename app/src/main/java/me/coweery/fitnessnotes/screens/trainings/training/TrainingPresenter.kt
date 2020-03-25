package me.coweery.fitnessnotes.screens.trainings.training

import io.reactivex.Single
import io.reactivex.rxkotlin.zipWith
import me.coweery.fitnessnotes.data.trainings.Training
import me.coweery.fitnessnotes.data.trainings.TrainingsService
import me.coweery.fitnessnotes.data.trainings.exercises.Exercise
import me.coweery.fitnessnotes.data.trainings.exercises.ExercisesService
import me.coweery.fitnessnotes.data.trainings.exercises.sets.Set
import me.coweery.fitnessnotes.data.trainings.exercises.sets.SetsService
import me.coweery.fitnessnotes.screens.BasePresenter
import me.coweery.fitnessnotes.screens.trainings.training.input.ExerciseInputContext
import me.coweery.fitnessnotes.screens.trainings.training.input.SetInputContext
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

class TrainingPresenter @Inject constructor(
    private val exercisesService: ExercisesService,
    private val trainingsService: TrainingsService,
    private val setsService: SetsService
) : BasePresenter<TrainingContract.View>(),
    TrainingContract.Presenter {

    private var mTraining: Training? = null

    private val simpleDateFormat = SimpleDateFormat("dd MMM YY HH:mm")

    override fun onAddExercisesClicked() {
        view?.showExerciseInput(
            ExerciseInputContext(null, null, null, null, null, null)
        )
    }

    override fun onExercisesDataReceived(exerciseInputContext: ExerciseInputContext) {

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
                if (exerciseInputContext.id == null) {
                    exercisesService.create(
                        Exercise(
                            null,
                            exerciseInputContext.name!!,
                            it.id!!,
                            exerciseInputContext.weight!!,
                            exerciseInputContext.count!!,
                            exerciseInputContext.sets!!
                        )
                    )
                } else {
                    val exercise = Exercise(
                        exerciseInputContext.id,
                        exerciseInputContext.name!!,
                        it.id!!,
                        exerciseInputContext.weight!!,
                        exerciseInputContext.count!!,
                        exerciseInputContext.sets!!
                    )
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
        view?.showExerciseInput(
            ExerciseInputContext(
                exercise.id,
                exercise.name,
                exercise.trainingId,
                exercise.weight,
                exercise.count,
                exercise.sets
            )
        )
    }

    override fun onSetClicked(exercise: Exercise, set: Set?, setIndex: Int) {

        view?.showSetInput(
            set?.let {
                SetInputContext(it.id!!, exercise.id!!, it.repsCount, it.weight, setIndex)
            } ?: SetInputContext(null, exercise.id!!, exercise.count, exercise.weight, setIndex)
        )
    }

    override fun onSetDataReceived(setInputContext: SetInputContext) {

        setsService.createOrUpdate(
            Set(
                setInputContext.id,
                setInputContext.exerciseId,
                setInputContext.repsCount!!,
                setInputContext.weight!!,
                setInputContext.index
            )
        )
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