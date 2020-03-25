package me.coweery.fitnessnotes.screens.trainings.training

import me.coweery.fitnessnotes.data.trainings.exercises.Exercise
import me.coweery.fitnessnotes.data.trainings.exercises.sets.Set
import me.coweery.fitnessnotes.screens.MvpContract
import me.coweery.fitnessnotes.screens.trainings.training.input.ExerciseInputContext
import me.coweery.fitnessnotes.screens.trainings.training.input.SetInputContext

interface TrainingContract {

    interface View : MvpContract.View, ExercisesOutput, SetsOutput {

        fun addExercise(exercise: Exercise)

        fun showExerciseInput(exerciseInputContext: ExerciseInputContext)

        fun deleteExercise(id : Long)

        fun showSetInput(setInputContext: SetInputContext)

        fun addSet(set : Set)
    }

    interface Presenter : MvpContract.Presenter<View> {

        fun onTrainingReceived(trainingId : Long)

        fun onAddExercisesClicked()

        fun onExercisesDataReceived(exerciseInputContext: ExerciseInputContext)

        fun onExerciseDeleteClicked(exercise: Exercise)

        fun onExerciseEditClicked(exercise: Exercise)

        fun onSetDataReceived(setInputContext: SetInputContext)

        fun onSetClicked(exercise: Exercise, set : Set?, setIndex : Int)
    }

    interface ExercisesOutput {

        fun onDataReceived(exerciseInputContext: ExerciseInputContext)
    }

    interface SetsOutput {

        fun onSetDataReceived(setInputContext: SetInputContext)
    }
}