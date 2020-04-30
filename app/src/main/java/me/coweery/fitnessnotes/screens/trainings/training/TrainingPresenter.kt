package me.coweery.fitnessnotes.screens.trainings.training

import io.reactivex.Single
import io.reactivex.rxkotlin.zipWith
import me.coweery.fitnessnotes.data.trainings.Training
import me.coweery.fitnessnotes.data.trainings.TrainingsService
import me.coweery.fitnessnotes.data.trainings.exercises.Exercise
import me.coweery.fitnessnotes.data.trainings.exercises.ExerciseWithSets
import me.coweery.fitnessnotes.data.trainings.exercises.ExercisesService
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

    private var mTraining: Training? = null
    private var exercisesCount: Int = 0

    private val simpleDateFormat = SimpleDateFormat("dd MMM YY HH:mm")

    override fun onAddExercisesClicked() {
        view?.showExerciseInput(
            ExerciseInputContext(null, null, mTraining?.id, null, null, null, exercisesCount)
        )
    }

    override fun onExercisesDataReceived(exerciseInputContext: ExerciseInputContext) {

        if (mTraining == null) {
            val creationDate = Date()
            with(
                Training(
                    null,
                    "Тренировка от ${simpleDateFormat.format(creationDate)}",
                    false,
                    creationDate,
                    creationDate
                )
            ) {
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
                            exerciseInputContext.setsCount!!,
                            exerciseInputContext.index
                        )
                    )
                        .map {
                            exercisesCount += 1
                            it to true
                        }
                } else {
                    val exercise = Exercise(
                        exerciseInputContext.id,
                        exerciseInputContext.name!!,
                        it.id!!,
                        exerciseInputContext.weight!!,
                        exerciseInputContext.count!!,
                        exerciseInputContext.setsCount!!,
                        exerciseInputContext.index
                    )
                    exercisesService.update(exercise)
                        .andThen(Single.just(exercise to false))
                }
            }
            .safetySubscribe(
                { (exercise, isCreated) ->
                    if (isCreated) {
                        view?.addExercise(exercise)
                    } else {
                        view?.updateExercise(exercise)
                    }
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
                    exercisesCount = it.exercises.size
                    it.exercises
                        .asSequence()
                        .sortedBy { it.exercise.index }
                        .forEach {
                            view?.addExercise(it)
                        }
                },
                {
                    it.printStackTrace()
                },
                {}
            )
    }

    override fun onExerciseDeleteClicked(exercise: Exercise) {

        exercisesService.delete(exercise.id!!)
            .safetySubscribe(
                {
                    view?.deleteExercise(exercise.id)
                },
                {
                    it.printStackTrace()
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
                exercise.sets,
                exercise.index
            )
        )
    }

    override fun onSetClicked(exercise: ExerciseWithSets, setIndex: Int) {

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

    override fun onSetDeleteClicked(setId: Long?) {
        setId?.let {
            setsService.delete(setId)
                .subscribe {
                    view?.deleteSet(it)
                }
        }
    }

    override fun onExercisesIndexesChanged(changedExercises: List<Pair<Int, Exercise>>) {

        changedExercises.map { (newIndex, exercise) ->
            exercise.copy(index = newIndex)
        }
            .let {
                exercisesService.updateAll(it).andThen(Single.just(it))
            }
            .safetySubscribe(
                {
                    it.forEach { view?.updateExercise(it) }
                },
                {
                    it.printStackTrace()
                }
            )
    }
}