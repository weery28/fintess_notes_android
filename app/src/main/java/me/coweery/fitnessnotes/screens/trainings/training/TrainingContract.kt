package me.coweery.fitnessnotes.screens.trainings.training

import me.coweery.fitnessnotes.data.trainings.exercises.Exercise
import me.coweery.fitnessnotes.screens.MvpContract

interface TrainingContract {

    interface View : MvpContract.View, Output {

        fun addExercise(exercise: Exercise)

        fun showExerciseInput()

        fun showActiveTrainingScreen()

        fun showStoppedTrainingScreen()

        fun deleteExercise(id : Long)
    }

    interface Presenter : MvpContract.Presenter<View> {

        fun onAddExercisesClicked()

        fun onTrainingReceived(trainingId : Long)

        fun onExercisesDataReceived(
            name : String,
            weight : Float,
            count : Int,
            sets : Int
        )

        fun onStartTrainingClicked()

        fun onExerciseDeleteClicked(exercise: Exercise)
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