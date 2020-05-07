package me.coweery.fitnessnotes.screens.trainings.training

import me.coweery.fitnessnotes.data.trainings.exercises.Exercise
import me.coweery.fitnessnotes.data.trainings.exercises.sets.Set
import me.coweery.fitnessnotes.screens.MvpContract

interface TrainingContract {

    interface View : MvpContract.View {

        fun showExercise(exercise: Exercise)

        fun showUpdateExerciseInput(exercise: Exercise)

        fun showSet(set: Set)

        fun showCreateExerciseInput(trainingId: Long)

        fun showCreateSetInput(exerciseId: Long)

        fun deleteExercise(id: Long)

        fun deleteSet(id: Long)

        fun showSetInput(set: Set)

        fun actualizeIndexes()
    }

    interface Presenter : MvpContract.Presenter<View> {

        fun onTrainingReceived(trainingId: Long)

        fun onAddExercisesClicked()

        fun onExercisesDataReceived(exercise: Exercise)

        fun onExerciseDeleteClicked(exerciseId: Long)

        fun onExerciseEditClicked(exercise: Exercise)

        fun onSetDataReceived(set: Set)

        fun onSetClicked(exercise: Exercise, set: Set?, setIndex: Int)

        fun onSetDeleteClicked(setId: Long)

        fun onExercisesIndexesChanged(changedExercises: List<Pair<Int, Exercise>>)
    }

    interface ExercisesOutput {

        fun onExerciseDataReceived(exercise: Exercise)
    }

    interface SetsOutput {

        fun onSetDataReceived(set: Set)

        fun onSetDeleteClicked(setId: Long)
    }
}