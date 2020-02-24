package me.coweery.fitnessnotes.screens.trainings.list

import android.widget.ListView
import me.coweery.fitnessnotes.R
import me.coweery.fitnessnotes.context.AppContext
import me.coweery.fitnessnotes.data.trainings.Training
import me.coweery.fitnessnotes.screens.BaseActivity
import me.coweery.fitnessnotes.screens.trainings.list.list.TrainingsListAdapter
import javax.inject.Inject

class TrainingsListActivity : BaseActivity<TrainingsListContract.View, TrainingsListContract.Presenter>(), TrainingsListContract.View {

    @Inject
    override lateinit var presenter: TrainingsListContract.Presenter

    private val trainingsList by lazy { findViewById<ListView>(R.id.lv_trainings_list) }

    override fun setupDI() {
        AppContext.appComponent.trainingsListScreenComponent().inject(this)
    }

    override fun onStart() {
        super.onStart()
        setContentView(R.layout.activity_trainings_list)
    }

    override fun onResume() {
        super.onResume()
        presenter.onScreenLoaded()

    }

    override fun showTrainings(list: List<Training>) {

        trainingsList.adapter = TrainingsListAdapter(this,list)
    }
}