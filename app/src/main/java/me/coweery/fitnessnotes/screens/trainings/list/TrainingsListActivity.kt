package me.coweery.fitnessnotes.screens.trainings.list

import android.content.Intent
import android.os.Bundle
import android.widget.ListAdapter
import android.widget.ListView
import com.google.android.material.floatingactionbutton.FloatingActionButton
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
    private val addTrainingButton by lazy { findViewById<FloatingActionButton>(R.id.fab_add) }
    private lateinit var adapter : TrainingsListAdapter

    override fun setupDI() {
        AppContext.appComponent.trainingsListScreenComponent().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_with_list_toolbar_and_add_button)
        setupToolbar()
        addTrainingButton.setOnClickListener {
            presenter.onAddTrainingClicked()
        }
        adapter = TrainingsListAdapter(this)
        trainingsList.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        presenter.onScreenLoaded()

    }

    override fun showTrainings(list: List<Training>) {
        adapter.trainings.clear()
        adapter.trainings.addAll(list)
        adapter.notifyDataSetChanged()
    }

    override fun showCreateTrainingScreen() {
        startActivity(Intent(this, TrainingActivity::class.java))
    }
}