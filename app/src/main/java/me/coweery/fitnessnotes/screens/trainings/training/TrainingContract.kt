package me.coweery.fitnessnotes.screens.trainings.training

import me.coweery.fitnessnotes.data.exercises.Exercise
import me.coweery.fitnessnotes.screens.MvpContract

interface TrainingContract {

    interface View : MvpContract.View {

        fun addExercise(exercise: Exercise)

        fun showExerciseInput()
    }

    interface Presenter : MvpContract.Presenter<View>, Output {

        fun onAddExercisesClicked()
    }

    interface Output {

        fun onDataReceived(
            name : String,
            weight : Float,
            count : Int,
            sets : Int
        )
    }
}