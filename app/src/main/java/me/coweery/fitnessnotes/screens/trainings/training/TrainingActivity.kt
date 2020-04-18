package me.coweery.fitnessnotes.screens.trainings.training

import android.os.Bundle
import android.widget.ListView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.woxthebox.draglistview.DragListView
import io.reactivex.Single
import me.coweery.fitnessnotes.R
import me.coweery.fitnessnotes.context.AppContext
import me.coweery.fitnessnotes.data.trainings.exercises.Exercise
import me.coweery.fitnessnotes.data.trainings.exercises.sets.Set
import me.coweery.fitnessnotes.screens.BaseActivity
import me.coweery.fitnessnotes.screens.trainings.IntentKey
import me.coweery.fitnessnotes.screens.trainings.training.input.ExerciseInputContext
import me.coweery.fitnessnotes.screens.trainings.training.input.exercise.InputExerciseFragment
import me.coweery.fitnessnotes.screens.trainings.training.input.InputSetFragment
import me.coweery.fitnessnotes.screens.trainings.training.input.SetInputContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TrainingActivity : BaseActivity<TrainingContract.View, TrainingContract.Presenter>(),
    TrainingContract.View {

    @Inject
    override lateinit var presenter: TrainingContract.Presenter

    private lateinit var exercisesInput: InputExerciseFragment
    private lateinit var setsInput: DialogFragment
    private val exercisesList by lazy { findViewById<DragListView>(R.id.lv_trainings_list) }
    private val addExerciseButton by lazy { findViewById<FloatingActionButton>(R.id.fab_add) }
    private var trainigId: Long? = null

    private lateinit var adapter: ExercisesAdapter

    override fun setupDI() {
        AppContext.appComponent.trainingScreenComponent().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_training)
        trainigId = intent.extras?.getLong(IntentKey.TRAINING_ID)
        setupToolbar(getString(R.string.training))
        adapter = NewExercisesAdapter(
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
        exercisesInput = InputExerciseFragment(
            this
        )

        AppContext.appComponent.trainingScreenComponent().inject(exercisesInput)

        setsInput = InputSetFragment(this)

        exercisesList.setLayoutManager(LinearLayoutManager(this))
        exercisesList.setCanDragHorizontally(false)
        exercisesList.setAdapter(adapter as NewExercisesAdapter, false)


        exercisesList.setDragListCallback(object : DragListView.DragListCallbackAdapter(){
            override fun canDragItemAtPosition(dragPosition: Int): Boolean {
                return  true
            }

            override fun canDropItemAtPosition(dropPosition: Int): Boolean {
                return true
            }
        })
        exercisesList.setDragListListener(object : DragListView.DragListListenerAdapter() {

            override fun onItemDragging(itemPosition: Int, x: Float, y: Float) {
                println(itemPosition)
            }

            override fun onItemDragEnded(fromPosition: Int, toPosition: Int) {
                println(fromPosition)
                println(toPosition)
            }

            override fun onItemDragStarted(position: Int) {
                println(position)
            }
        })

        trainigId?.let { presenter.onTrainingReceived(it) }
    }

    override fun addExercise(exercise: Exercise) {
        adapter.add(exercise)
    }

    override fun deleteExercise(id: Long) {
        adapter.deleteExercise(id)
    }

    override fun onDataReceived(exerciseInputContext: ExerciseInputContext) {
        presenter.onExercisesDataReceived(exerciseInputContext)
    }

    override fun showExerciseInput(exerciseInputContext: ExerciseInputContext) {
        exercisesInput.arguments = Bundle().apply {
            putSerializable("exercise", exerciseInputContext)
        }
        exercisesInput.show(supportFragmentManager, "setInput")
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

    override fun onSetDeleted(setId: Long?) {
        presenter.onSetDeleteClicked(setId)
    }

    override fun deleteSet(id: Long) {
        adapter.deleteSet(id)
    }
}