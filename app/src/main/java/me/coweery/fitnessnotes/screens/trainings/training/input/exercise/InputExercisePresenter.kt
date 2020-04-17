package me.coweery.fitnessnotes.screens.trainings.training.input.exercise

import me.coweery.fitnessnotes.data.trainings.exercises.ExercisesService
import me.coweery.fitnessnotes.screens.BasePresenter
import javax.inject.Inject

class InputExercisePresenter @Inject constructor(
    private val exercisesService: ExercisesService
) : InputExerciseContract.Presenter, BasePresenter<InputExerciseContract.View>() {


    override fun onTextChanged(text: String, trainingId: Long?) {

        exercisesService.getLastCompletion(text, trainingId)
            .safetySubscribe(
                {
                    view?.showLastCompletion(
                        it.date,
                        it.exercise.count,
                        it.exercise.weight,
                        it.exercise.sets,
                        it.sets
                    )
                },
                {
                    it.printStackTrace()
                },
                {
                    view?.clearLastCompletion()
                }
            )

    }
}