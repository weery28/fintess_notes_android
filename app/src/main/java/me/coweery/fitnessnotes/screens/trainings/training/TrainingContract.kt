package me.coweery.fitnessnotes.screens.trainings.training

import me.coweery.fitnessnotes.data.trainings.exercises.Exercise
import me.coweery.fitnessnotes.data.trainings.exercises.sets.Set
import me.coweery.fitnessnotes.screens.MvpContract

interface TrainingContract {

    interface View : MvpContract.View, ExercisesOutput, SetsOutput {

        fun addExercise(exercise: Exercise)

        fun showExerciseInput(exercise: Exercise)

        fun deleteExercise(id : Long)

        fun showSetInput(set : Set)

        fun addSet(set : Set)
    }

    interface Presenter : MvpContract.Presenter<View> {

        fun onAddExercisesClicked()

        fun onTrainingReceived(trainingId : Long)

        fun onExercisesDataReceived(exercise: Exercise)

        fun onSetDataReceived(set : Set)

        fun onExerciseDeleteClicked(exercise: Exercise)

        fun onExerciseEditClicked(exercise: Exercise)

        fun onSetClicked(exercise: Exercise, set : Set?, setIndex : Int)
    }

    interface ExercisesOutput {

        fun onDataReceived(exercise: Exercise)
    }

    interface SetsOutput {

        fun onSetDataReceived(set : Set)
    }
}