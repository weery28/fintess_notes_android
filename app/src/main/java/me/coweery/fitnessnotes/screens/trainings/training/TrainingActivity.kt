package me.coweery.fitnessnotes.screens.trainings.training

import android.os.Bundle
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
import me.coweery.fitnessnotes.screens.trainings.training.input.ExerciseInputContext
import me.coweery.fitnessnotes.screens.trainings.training.input.InputExerciseFragment
import me.coweery.fitnessnotes.screens.trainings.training.input.InputSetFragment
import me.coweery.fitnessnotes.screens.trainings.training.input.SetInputContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TrainingActivity : BaseActivity<TrainingContract.View, TrainingContract.Presenter>(),
    TrainingContract.View {

    @Inject
    override lateinit var presenter: TrainingContract.Presenter

    private lateinit var input : DialogFragment
    private lateinit var setsInput : DialogFragment

    private val exercisesList by lazy { findViewById<ListView>(R.id.lv_trainings_list) }
    private val addExerciseButton by lazy { findViewById<FloatingActionButton>(R.id.fab_add) }
    private var trainigId : Long? = null

    private lateinit var adapter : ExercisesListAdapter

    override fun setupDI() {
        AppContext.appComponent.trainingScreenComponent().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_training)
        trainigId = intent.extras?.getLong(IntentKey.TRAINING_ID)
        setupToolbar(getString(R.string.training))
        adapter = ExercisesListAdapter(
            this,
            presenter::onExerciseDeleteClicked,
            presenter::onSetClicked,
            presenter::onExerciseEditClicked
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
        setsInput = InputSetFragment(this)
        exercisesList.adapter = adapter

        trainigId?.let { presenter.onTrainingReceived(it) }
    }


    override fun addExercise(exercise: Exercise) {
        adapter.add(exercise)
    }

    override fun deleteExercise(id: Long) {
        adapter.delete(id)
    }

    override fun onDataReceived(exerciseInputContext: ExerciseInputContext) {
        presenter.onExercisesDataReceived(exerciseInputContext)
    }

    override fun showExerciseInput(exerciseInputContext: ExerciseInputContext) {
        input.arguments = Bundle().apply {
            putSerializable("exercise", exerciseInputContext)
        }
        input.show(supportFragmentManager, "setInput")
    }

    override fun onSetDataReceived(setInputContext: SetInputContext) {
        presenter.onSetDataReceived(setInputContext)
    }

    override fun showSetInput(setInputContext: SetInputContext) {

        setsInput.arguments = Bundle().apply {
            putSerializable("set", setInputContext)
        }
        setsInput.show(supportFragmentManager, "setInput")

    }

    override fun addSet(set: Set) {
        adapter.add(set)
    }
}