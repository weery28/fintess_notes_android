package me.coweery.fitnessnotes.screens.trainings.list

import me.coweery.fitnessnotes.data.trainings.Training
import me.coweery.fitnessnotes.screens.MvpContract
import java.util.*

interface TrainingsListContract {

    interface View : MvpContract.View {

        fun showTrainings(list: List<Training>)

        fun showCreateTrainingScreen()

        fun showTrainingScreen(id: Long)
    }

    interface Presenter : MvpContract.Presenter<View> {

        fun onScreenLoaded()

        fun onAddTrainingClicked()

        fun onTrainingClicked(id: Long)

        fun onTrainingDataReceived(name : String, date : Date)

    }

    interface Output {

        fun onTrainingDataReceived(name : String, date : Date)
    }
}