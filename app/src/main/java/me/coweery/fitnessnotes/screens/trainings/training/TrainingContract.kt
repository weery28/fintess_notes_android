package me.coweery.fitnessnotes.screens.trainings.training

import me.coweery.fitnessnotes.data.exercises.Exercise
import me.coweery.fitnessnotes.screens.MvpContract

interface TrainingContract {

    interface View : MvpContract.View, Output {

        fun addExercise(exercise: Exercise)

        fun showExerciseInput()

        fun showEditScreen()

        fun showPreparedTrainingScreen()
    }

    interface Presenter : MvpContract.Presenter<View> {

        fun onAddExercisesClicked()

        fun onEditingDone()

        fun onEditClicked()

        fun onTrainingReceived(trainingId : Long)

        fun onExercisesDataReceived(
            name : String,
            weight : Float,
            count : Int,
            sets : Int
        )
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