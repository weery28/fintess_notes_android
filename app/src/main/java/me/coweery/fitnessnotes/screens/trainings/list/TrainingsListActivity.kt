package me.coweery.fitnessnotes.screens.trainings.list

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import me.coweery.fitnessnotes.R
import me.coweery.fitnessnotes.context.AppContext
import me.coweery.fitnessnotes.data.trainings.Training
import me.coweery.fitnessnotes.screens.BaseActivity
import me.coweery.fitnessnotes.screens.trainings.IntentKey
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
    private lateinit var adapter: TrainingsListAdapter

    override fun setupDI() {
        AppContext.appComponent.trainingsListScreenComponent().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trainings)
        setupToolbar()
        addTrainingButton.setOnClickListener {
            presenter.onAddTrainingClicked()
        }
        adapter = TrainingsListAdapter(this) {
            presenter.onTrainingClicked(it.id!!)
        }
        trainingsList.adapter = adapter

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.app_name, R.string.app_name
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()
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
        startActivity(
            Intent(this, TrainingActivity::class.java)
        )
    }

    override fun showEditTrainingScreen(id: Long) {
        startActivity(
            Intent(this, TrainingActivity::class.java)
                .putExtra(IntentKey.TRAINING_ID, id)
        )
    }
}