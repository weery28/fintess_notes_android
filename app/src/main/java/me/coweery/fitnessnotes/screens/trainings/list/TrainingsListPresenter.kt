package me.coweery.fitnessnotes.screens.trainings.list

import me.coweery.fitnessnotes.data.trainings.TrainingsService
import me.coweery.fitnessnotes.screens.BasePresenter
import javax.inject.Inject

class TrainingsListPresenter @Inject constructor(
    private val trainingsService: TrainingsService
) : BasePresenter<TrainingsListContract.View>(), TrainingsListContract.Presenter {

    override fun onScreenLoaded() {

        trainingsService.getAll()
            .safetySubscribe(
                {
                    view?.showTrainings(it.sortedBy { it.isComplete })
                },
                {
                    it.printStackTrace()
                }
            )
    }
}