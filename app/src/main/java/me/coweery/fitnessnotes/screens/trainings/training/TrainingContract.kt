package me.coweery.fitnessnotes.screens.trainings.training

import me.coweery.fitnessnotes.data.trainings.exercises.Exercise
import me.coweery.fitnessnotes.data.trainings.exercises.ExerciseWithSets
import me.coweery.fitnessnotes.screens.MvpContract

interface TrainingContract {

    interface View : MvpContract.View, ExercisesOutput, SetsOutput {

        fun addExercise(exercise: ExerciseWithSets)

        fun updateExercise(exercise: ExerciseWithSets)

        fun showUpdateExerciseInput(exercise: ExerciseWithSets)

        fun showCreateExerciseInput(trainingId: Long)

        fun deleteExercise(id: Long)

        fun showSetInput(exercise: ExerciseWithSets)
    }

    interface Presenter : MvpContract.Presenter<View> {

        fun onTrainingReceived(trainingId: Long)

        fun onAddExercisesClicked()

        fun onExercisesDataReceived(exercise: ExerciseWithSets)

        fun onExerciseDeleteClicked(exercise: Exercise)

        fun onExerciseEditClicked(exercise: Exercise)

        fun onSetDataReceived(exercise: ExerciseWithSets)

        fun onSetClicked(exercise: ExerciseWithSets, setIndex: Int)

        fun onSetDeleteClicked(setId: Long?)

        fun onExercisesIndexesChanged(changedExercises: List<Pair<Int, Exercise>>)
    }

    interface ExercisesOutput {

        fun onDataReceived(exercise: ExerciseWithSets)
    }

    interface SetsOutput {

        fun onSetDataReceived(exercise: ExerciseWithSets)

        fun onSetDeleted(setId: Long?)
    }
}