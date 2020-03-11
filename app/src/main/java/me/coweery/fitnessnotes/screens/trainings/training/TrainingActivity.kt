package me.coweery.fitnessnotes.screens.trainings.training

import android.os.Bundle
import android.widget.ListView
import androidx.fragment.app.DialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import me.coweery.fitnessnotes.R
import me.coweery.fitnessnotes.context.AppContext
import me.coweery.fitnessnotes.data.exercises.Exercise
import me.coweery.fitnessnotes.screens.BaseActivity
import me.coweery.fitnessnotes.screens.trainings.training.input.InputExerciseFragment
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_with_list_toolbar_and_add_button)
        setupToolbar()
        adapter = ExercisesListAdapter(this)
        addExerciseButton.setOnClickListener {
            presenter.onAddExercisesClicked()
        }
        input = InputExerciseFragment(presenter)
        exercisesList.adapter = adapter
    }

    override fun addExercise(exercise: Exercise) {

        adapter.exercises.add(exercise)
        adapter.notifyDataSetChanged()
    }

    override fun showExerciseInput() {
        input.show(supportFragmentManager, "input")
    }
}