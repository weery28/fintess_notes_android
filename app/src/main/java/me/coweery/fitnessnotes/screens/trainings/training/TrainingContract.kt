package me.coweery.fitnessnotes.screens.trainings.training

import me.coweery.fitnessnotes.screens.MvpContract

interface TrainingContract {

    interface View : MvpContract.View

    interface Presenter : MvpContract.Presenter<View>
}