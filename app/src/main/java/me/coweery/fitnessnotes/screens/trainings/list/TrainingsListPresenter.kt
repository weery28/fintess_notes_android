package me.coweery.fitnessnotes.screens.trainings.list

import me.coweery.fitnessnotes.data.trainings.Training
import me.coweery.fitnessnotes.data.trainings.TrainingsService
import me.coweery.fitnessnotes.screens.BasePresenter
import java.util.*
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

    override fun onTrainingDataReceived(name: String, date: Date) {

        trainingsService.save(Training(null, name, false, Date(), date))
            .safetySubscribe(
                {
                    view?.showTrainingScreen(it)
                },
                {
                    it.printStackTrace()
                }
            )
    }

    override fun onAddTrainingClicked() {

        view?.showCreateTrainingScreen()
    }

    override fun onTrainingClicked(id: Long) {
        view?.showTrainingScreen(id)
    }
}