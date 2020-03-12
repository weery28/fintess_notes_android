package me.coweery.fitnessnotes.screens.trainings.training

import me.coweery.fitnessnotes.data.trainings.exercises.Exercise
import me.coweery.fitnessnotes.data.trainings.exercises.sets.Set
import me.coweery.fitnessnotes.screens.MvpContract

interface TrainingContract {

    interface View : MvpContract.View, Output {

        fun addExercise(exercise: Exercise)

        fun showExerciseInput()

        fun showActiveTrainingScreen()

        fun showStoppedTrainingScreen()

        fun deleteExercise(id : Long)

        fun showSetInput(defaultWeight : Float, defaultCount : Int, result : (Float, Int) -> Unit)

        fun addSet(set : Set)
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

        fun onSetClicked(exercise: Exercise, setIndex : Int)
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