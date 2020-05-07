package me.coweery.fitnessnotes.screens.trainings.training

import io.reactivex.Single
import me.coweery.fitnessnotes.data.trainings.exercises.Exercise
import me.coweery.fitnessnotes.data.trainings.exercises.ExercisesService
import me.coweery.fitnessnotes.data.trainings.exercises.sets.Set
import me.coweery.fitnessnotes.data.trainings.exercises.sets.SetsService
import me.coweery.fitnessnotes.screens.BasePresenter
import javax.inject.Inject

class TrainingPresenter @Inject constructor(
    private val exercisesService: ExercisesService,
    private val setsService: SetsService
) : BasePresenter<TrainingContract.View>(),
    TrainingContract.Presenter {

    private var mTrainingId = -1L

    override fun onTrainingReceived(trainingId: Long) {
        mTrainingId = trainingId
        exercisesService.getFullByTrainingId(trainingId)
            .safetySubscribe({
                it.asSequence()
                    .sortedBy { it.exercise.index }
                    .forEach {
                        view?.showExercise(it.exercise)
                        it.sets.forEach {
                            view?.showSet(it)
                        }
                    }
            },
                {
                    it.printStackTrace()
                }
            )
    }

    override fun onAddExercisesClicked() {
        view?.showCreateExerciseInput(mTrainingId)
    }

    override fun onExercisesDataReceived(exercise: Exercise) {

        if (exercise.id != null) {
            exercisesService.update(exercise)
                .andThen(Single.just(exercise))

        } else {
            exercisesService.getExecrciseCount(mTrainingId)
                .flatMap {
                    exercisesService.create(exercise.copy(index = it))
                }
        }
            .safetySubscribe(
                {
                    view?.showExercise(it)
                },
                {
                    it.printStackTrace()
                }
            )
    }

    override fun onExerciseDeleteClicked(exerciseId: Long) {

        exercisesService.delete(exerciseId)
            .safetySubscribe(
                {
                    view?.deleteExercise(exerciseId)
                },
                {
                    it.printStackTrace()
                })
    }

    override fun onExerciseEditClicked(exercise: Exercise) {
        view?.showUpdateExerciseInput(exercise)
    }

    override fun onSetClicked(exercise: Exercise, set: Set?, setIndex: Int) {

        if (set == null) {

            view?.showSetInput(
                Set(null, exercise.id!!, exercise.count, exercise.weight, setIndex)
            )
        } else {
            view?.showSetInput(set)

        }
    }


    override fun onSetDataReceived(set: Set) {

        setsService.createOrUpdate(set)
            .safetySubscribe(
                {
                    view?.showSet(it)
                },
                {
                    it.printStackTrace()
                }
            )
    }

    override fun onExercisesIndexesChanged(changedExercises: List<Pair<Int, Exercise>>) {

        changedExercises.map { (newIndex, exercise) ->
            exercise.copy(index = newIndex)
        }
            .let {
                exercisesService.updateAll(it)
            }
            .safetySubscribe(
                {
                    view?.actualizeIndexes()
                },
                {
                    it.printStackTrace()
                }
            )
    }

    override fun onSetDeleteClicked(setId: Long) {

        setsService.delete(setId)
            .safetySubscribe(
                {
                    view?.deleteSet(setId)
                },
                {
                    it.printStackTrace()
                }
            )

    }
}