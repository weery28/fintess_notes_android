package me.coweery.fitnessnotes.screens.trainings.list

import android.content.Intent
import android.widget.ListView
import me.coweery.fitnessnotes.R
import me.coweery.fitnessnotes.context.AppContext
import me.coweery.fitnessnotes.data.trainings.Training
import me.coweery.fitnessnotes.screens.BaseActivity
import me.coweery.fitnessnotes.screens.trainings.list.adapter.TrainingsListAdapter
import me.coweery.fitnessnotes.screens.trainings.training.TrainingActivity
import javax.inject.Inject

class TrainingsListActivity :
    BaseActivity<TrainingsListContract.View, TrainingsListContract.Presenter>(),
    TrainingsListContract.View {

    @Inject
    override lateinit var presenter: TrainingsListContract.Presenter

    private val trainingsList by lazy { findViewById<ListView>(R.id.lv_trainings_list) }
    private val addTrainingButton by lazy { findViewById<ListView>(R.id.fab_add) }

    override fun setupDI() {
        AppContext.appComponent.trainingsListScreenComponent().inject(this)
    }

    override fun onStart() {
        super.onStart()
        setContentView(R.layout.activity_with_list_toolbar_and_add_button)
        addTrainingButton.setOnClickListener {
            presenter.onAddTrainingClicked()
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.onScreenLoaded()

    }

    override fun showTrainings(list: List<Training>) {

        trainingsList.adapter = TrainingsListAdapter(this, list)
    }

    override fun showCreateTrainingScreen() {
        startActivity(Intent(this, TrainingActivity::class.java))
    }
}