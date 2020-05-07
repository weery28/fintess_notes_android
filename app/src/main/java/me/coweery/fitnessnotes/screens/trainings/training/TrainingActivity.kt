package me.coweery.fitnessnotes.screens.trainings.training

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.woxthebox.draglistview.DragListView
import io.reactivex.Single
import me.coweery.fitnessnotes.R
import me.coweery.fitnessnotes.context.AppContext
import me.coweery.fitnessnotes.data.trainings.exercises.Exercise
import me.coweery.fitnessnotes.data.trainings.exercises.sets.Set
import me.coweery.fitnessnotes.screens.BaseActivity
import me.coweery.fitnessnotes.screens.trainings.IntentKey
import me.coweery.fitnessnotes.screens.trainings.training.input.InputSetFragment
import me.coweery.fitnessnotes.screens.trainings.training.input.exercise.InputExerciseFragment
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TrainingActivity : BaseActivity<TrainingContract.View, TrainingContract.Presenter>(),
    TrainingContract.View, TrainingContract.ExercisesOutput, TrainingContract.SetsOutput {

    @Inject
    override lateinit var presenter: TrainingContract.Presenter

    private lateinit var exercisesInput: InputExerciseFragment
    private lateinit var setsInput: DialogFragment
    private val exercisesList by lazy { findViewById<DragListView>(R.id.lv_trainings_list) }
    private val addExerciseButton by lazy { findViewById<FloatingActionButton>(R.id.fab_add) }
    private var trainigId: Long? = null

    private lateinit var adapter: NewExercisesAdapter

    override fun setupDI() {
        AppContext.appComponent.trainingScreenComponent().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_training)
        setupToolbar(getString(R.string.training))
        setupList()

        addExerciseButton.setOnClickListener {
            addExerciseButton.isClickable = false
            Single.timer(1, TimeUnit.SECONDS)
                .subscribe { _, _ ->
                    addExerciseButton.isClickable = true
                }
            presenter.onAddExercisesClicked()
        }
        exercisesInput = InputExerciseFragment(this)
        AppContext.appComponent.trainingScreenComponent().inject(exercisesInput)

        setsInput = InputSetFragment(this)

        trainigId = intent.extras?.getLong(IntentKey.TRAINING_ID)
        trainigId?.let { presenter.onTrainingReceived(it) }
    }


    override fun deleteExercise(id: Long) {
        adapter.deleteExercise(id)
    }

    override fun showExercise(exercise: Exercise) {
        adapter.update(exercise)
    }

    override fun showUpdateExerciseInput(exercise: Exercise) {

        exercisesInput.arguments = Bundle().apply {
            putSerializable("exercise", exercise)
        }
        exercisesInput.show(supportFragmentManager, "exerciseInput")
    }

    override fun showSet(set: Set) {
        adapter.update(set)
    }

    override fun showCreateExerciseInput(trainingId: Long) {

        exercisesInput.arguments = Bundle().apply {
            putLong("trainingId", trainingId)
        }
        exercisesInput.show(supportFragmentManager, "exerciseInput")

    }

    override fun showCreateSetInput(exerciseId: Long) {
        setsInput.arguments = Bundle().apply {
            putLong("exerciseId", exerciseId)
        }
        setsInput.show(supportFragmentManager, "setInput")
    }

    override fun deleteSet(id: Long) {
        adapter.deleteSet(id)
    }

    override fun actualizeIndexes() {
        adapter.actualizeIndexes()
    }

    override fun onExerciseDataReceived(exercise: Exercise) {
        presenter.onExercisesDataReceived(exercise)
    }

    override fun onSetDataReceived(set: Set) {
        presenter.onSetDataReceived(set)
    }

    override fun showSetInput(set: Set) {

        setsInput.arguments = Bundle().apply {
            putSerializable("set", set)
        }
        setsInput.show(supportFragmentManager, "setInput")
    }

    override fun onSetDeleteClicked(setId: Long) {
        presenter.onSetDeleteClicked(setId)
    }

    private fun setupList() {

        adapter = NewExercisesAdapter(
            this,
            presenter::onExerciseDeleteClicked,
            presenter::onSetClicked,
            presenter::onExerciseEditClicked
        )

        val layoutManager = LinearLayoutManager(this)
        exercisesList.setLayoutManager(layoutManager)
        exercisesList.setCanDragHorizontally(false)
        exercisesList.setAdapter(adapter as NewExercisesAdapter, false)

        val dividerItemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        ContextCompat.getDrawable(this, R.drawable.transparent_horizontal_divider)
            ?.let(dividerItemDecoration::setDrawable)

        exercisesList.recyclerView.addItemDecoration(dividerItemDecoration)

        exercisesList.setDragListListener(
            object : DragListView.DragListListenerAdapter() {

                override fun onItemDragEnded(fromPosition: Int, toPosition: Int) {
                    adapter.itemList
                        .asSequence()
                        .mapIndexedNotNull { i, exercise ->
                            if (exercise.exercise.index != i) {
                                i to exercise.exercise
                            } else {
                                null
                            }
                        }
                        .toList()
                        .let {
                            presenter.onExercisesIndexesChanged(it)
                        }
                }
            }
        )
    }
}