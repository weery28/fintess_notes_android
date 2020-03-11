package me.coweery.fitnessnotes.screens.trainings.training

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ListView
import androidx.fragment.app.DialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.reactivex.Observable
import io.reactivex.Single
import me.coweery.fitnessnotes.R
import me.coweery.fitnessnotes.context.AppContext
import me.coweery.fitnessnotes.data.exercises.Exercise
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
    private val startTrainingButton by lazy { findViewById<Button>(R.id.btn_start) }
    private var trainigId : Long? = null
    private var menu : Menu? = null


    private lateinit var adapter : ExercisesListAdapter

    override fun setupDI() {
        AppContext.appComponent.trainingScreenComponent().inject(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        this.menu = menu

        if (trainigId == null){
            menuInflater.inflate(R.menu.menu_ok, menu)
        } else {
            menuInflater.inflate(R.menu.menu_edit, menu)
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.action_ok -> presenter.onEditingDone()
            R.id.action_edit -> presenter.onEditClicked()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_training)
        trainigId = intent.extras?.getLong(IntentKey.TRAINING_ID)
        setupToolbar()
        adapter = ExercisesListAdapter(this)
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

        if (trainigId != null){
            addExerciseButton.visibility = View.GONE
        } else {
            startTrainingButton.visibility = View.GONE
        }
    }

    override fun onResume() {

        super.onResume()
        trainigId?.let { presenter.onTrainingReceived(it) }

    }

    override fun addExercise(exercise: Exercise) {

        adapter.exercises.add(exercise)
        adapter.notifyDataSetChanged()
    }

    override fun showExerciseInput() {
        input.show(supportFragmentManager, "input")
    }

    override fun onDataReceived(name: String, weight: Float, count: Int, sets: Int) {
        presenter.onExercisesDataReceived(name, weight, count, sets)
    }

    override fun showEditScreen() {
        menu?.clear()
        menuInflater.inflate(R.menu.menu_ok, menu)
        addExerciseButton.visibility = View.VISIBLE
        startTrainingButton.visibility = View.GONE
    }

    override fun showPreparedTrainingScreen() {

        menu?.clear()
        menuInflater.inflate(R.menu.menu_edit, menu)
        addExerciseButton.visibility = View.GONE
        startTrainingButton.visibility = View.VISIBLE
    }
}