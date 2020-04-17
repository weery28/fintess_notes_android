package me.coweery.fitnessnotes.screens.trainings.training.input.exercise

import me.coweery.fitnessnotes.data.trainings.exercises.sets.Set
import me.coweery.fitnessnotes.screens.MvpContract
import java.util.*

interface InputExerciseContract {

    interface View : MvpContract.View {

        fun showLastCompletion(
            date: Date,
            countPlan: Int,
            weightPlan: Float,
            setsCount: Int,
            sets: List<Set>
        )

        fun clearLastCompletion()
    }

    interface Presenter : MvpContract.Presenter<View> {

        fun onTextChanged(text: String, trainingId: Long?)
    }
}