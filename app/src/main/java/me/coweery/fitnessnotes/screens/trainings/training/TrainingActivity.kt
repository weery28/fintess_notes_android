package me.coweery.fitnessnotes.screens.trainings.training

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ListView
import androidx.fragment.app.DialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.reactivex.Single
import me.coweery.fitnessnotes.R
import me.coweery.fitnessnotes.context.AppContext
import me.coweery.fitnessnotes.data.trainings.exercises.Exercise
import me.coweery.fitnessnotes.data.trainings.exercises.sets.Set
import me.coweery.fitnessnotes.screens.BaseActivity
import me.coweery.fitnessnotes.screens.trainings.IntentKey
import me.coweery.fitnessnotes.screens.trainings.training.input.InputExerciseFragment
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TrainingActivity : BaseActivity<TrainingContract.View, TrainingContract.Presenter>(),
    TrainingContract.View {

    @Inject
    override lateinit var presenter: TrainingContract.Presenter

    private lateinit var input : DialogFragment

    private val exercisesList by lazy { findViewById<ListView>(R.id.lv_trainings_list) }
    private val addExerciseButton by lazy { findViewById<FloatingActionButton>(R.id.fab_add) }
    private val startTrainingButton by lazy { findViewById<FloatingActionButton>(R.id.btn_start) }
    private var trainigId : Long? = null

    private lateinit var adapter : ExercisesListAdapter

    override fun setupDI() {
        AppContext.appComponent.trainingScreenComponent().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_training)
        trainigId = intent.extras?.getLong(IntentKey.TRAINING_ID)
        setupToolbar()
        adapter = ExercisesListAdapter(
            this,
            presenter::onExerciseDeleteClicked,
            presenter::onSetClicked
        )
        addExerciseButton.setOnClickListener {
            addExerciseButton.isClickable = false
            Single.timer(1, TimeUnit.SECONDS)
                .subscribe { _, _ ->
                    addExerciseButton.isClickable = true
                }
            presenter.onAddExercisesClicked()
        }
        input = InputExerciseFragment(this)
        exercisesList.adapter = adapter

        startTrainingButton.setOnClickListener {
            presenter.onStartTrainingClicked()
        }

        trainigId?.let { presenter.onTrainingReceived(it) }
    }


    override fun addExercise(exercise: Exercise) {
        adapter.add(exercise)
    }

    override fun deleteExercise(id: Long) {
        adapter.delete(id)
    }

    override fun showExerciseInput() {
        input.show(supportFragmentManager, "input")
    }

    override fun onDataReceived(name: String, weight: Float, count: Int, sets: Int) {
        presenter.onExercisesDataReceived(name, weight, count, sets)
    }

    override fun showActiveTrainingScreen() {
        startTrainingButton.setImageResource(R.drawable.baseline_stop_24)
        adapter.toActiveState()
    }

    override fun showStoppedTrainingScreen() {
        startTrainingButton.setImageResource(R.drawable.baseline_play_arrow_24)
        adapter.toStoppedState()
    }

    override fun showSetInput(
        defaultWeight: Float,
        defaultCount: Int,
        result: (Float, Int) -> Unit
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addSet(set: Set) {
        adapter.
    }
}