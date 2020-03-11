package me.coweery.fitnessnotes.screens.trainings.training

import me.coweery.fitnessnotes.data.exercises.Exercise
import me.coweery.fitnessnotes.screens.BasePresenter
import javax.inject.Inject

class TrainingPresenter @Inject constructor() : BasePresenter<TrainingContract.View>(),
    TrainingContract.Presenter {

    override fun onAddExercisesClicked() {
        view?.showExerciseInput()
    }

    override fun onDataReceived(name: String, weight: Float, count: Int, sets: Int) {
        view?.addExercise(
            Exercise(null, name, 0, weight, count, sets)
        )
    }
}