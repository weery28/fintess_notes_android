package me.coweery.fitnessnotes.screens.trainings.training

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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


    private lateinit var adapter : ExercisesListAdapter

    override fun setupDI() {
        AppContext.appComponent.trainingScreenComponent().inject(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_ok, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.action_ok){
            presenter.onEditingDone()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_with_list_toolbar_and_add_button)
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
    }

    override fun onResume() {
        super.onResume()
        intent.extras?.getLong(IntentKey.TRAINING_ID)?.let(presenter::onTrainingReceived)
    }

    override fun addExercise(exercise: Exercise) {

        adapter.exercises.add(exercise)
        adapter.notifyDataSetChanged()
    }

    override fun showExerciseInput() {
        input.show(supportFragmentManager, "input")
    }

    override fun closeScreen() {
        finish()
    }

    override fun onDataReceived(name: String, weight: Float, count: Int, sets: Int) {
        presenter.onExercisesDataReceived(name, weight, count, sets)
    }
}