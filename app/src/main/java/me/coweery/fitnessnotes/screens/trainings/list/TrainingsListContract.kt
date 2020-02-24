package me.coweery.fitnessnotes.screens.trainings.list

import me.coweery.fitnessnotes.data.trainings.Training
import me.coweery.fitnessnotes.screens.MvpContract

interface TrainingsListContract {

    interface View : MvpContract.View {

        fun showTrainings(list : List<Training>)
    }

    interface Presenter : MvpContract.Presenter<View> {

        fun onScreenLoaded()
    }
}