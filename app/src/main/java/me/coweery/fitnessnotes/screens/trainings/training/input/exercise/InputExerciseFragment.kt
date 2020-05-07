package me.coweery.fitnessnotes.screens.trainings.training.input.exercise

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import me.coweery.fitnessnotes.R
import me.coweery.fitnessnotes.data.trainings.exercises.Exercise
import me.coweery.fitnessnotes.data.trainings.exercises.sets.Set
import me.coweery.fitnessnotes.screens.trainings.training.TrainingContract
import me.coweery.fitnessnotes.screens.trainings.training.ifNotEmpty
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class InputExerciseFragment(
    private val exercisesOutput: TrainingContract.ExercisesOutput
) : DialogFragment(), InputExerciseContract.View {

    private lateinit var etName: EditText
    private lateinit var etWeight: EditText
    private lateinit var etCount: EditText
    private lateinit var etSets: EditText
    private lateinit var btnSave: View
    private lateinit var tvDate: TextView
    private lateinit var tvWeight: TextView
    private lateinit var tvCount: TextView
    private lateinit var llAproaches: LinearLayout
    private lateinit var lastCompletion: View
    private lateinit var tvCompletionNotFound: TextView

    private val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy")

    private var exercise: Exercise? = null
    private var trainingId: Long = 0

    @Inject
    lateinit var presenter: InputExerciseContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_exercise_input, container, false)
    }

    override fun onStart() {
        super.onStart()
        dialog!!.window!!
            .setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etName = view.findViewById(R.id.et_name)
        etWeight = view.findViewById(R.id.et_weight)
        etCount = view.findViewById(R.id.et_count)
        etSets = view.findViewById(R.id.et_sets)
        btnSave = view.findViewById(R.id.btn_save)
        tvDate = view.findViewById(R.id.tv_name)
        tvCount = view.findViewById(R.id.tv_count)
        tvWeight = view.findViewById(R.id.tv_weight)
        llAproaches = view.findViewById(R.id.ll_aproaches)
        tvCompletionNotFound = view.findViewById(R.id.tv_completion_not_found)
        lastCompletion = view.findViewById(R.id.last_completion)

        exercise = arguments?.getSerializable("exercise") as Exercise?
        trainingId = exercise?.trainingId ?: arguments?.getLong("trainingId")!!

        btnSave.setOnClickListener {
            exercisesOutput.onExerciseDataReceived(

                if (exercise == null) {
                    Exercise(
                        null,
                        name = etName.text.toString(),
                        weight = etWeight.text.toString().ifNotEmpty({ toFloat() }, 0f),
                        count = etCount.text.toString().ifNotEmpty({ toInt() }, 0),
                        sets = etSets.text.toString().ifNotEmpty({ toInt() }, 0),
                        trainingId = trainingId,
                        index = -1
                    )
                } else {
                    exercise!!.copy(
                        name = etName.text.toString(),
                        weight = etWeight.text.toString().ifNotEmpty({ toFloat() }, 0f),
                        count = etCount.text.toString().ifNotEmpty({ toInt() }, 0),
                        sets = etSets.text.toString().ifNotEmpty({ toInt() }, 0)
                    )
                }
            )
            dismissAllowingStateLoss()
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.atachView(this)
        etWeight.setText(exercise?.weight?.toString())
        etCount.setText(exercise?.count?.toString())
        etName.setText(exercise?.name)
        etSets.setText(exercise?.sets?.toString())
        presenter.onTextChanged(etName.text.toString(), exercise?.trainingId)

        etName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.onTextChanged(etName.text.toString(), exercise?.trainingId)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.disposeAll()
    }

    override fun showLastCompletion(
        date: Date,
        countPlan: Int,
        weightPlan: Float,
        setsCount: Int,
        sets: List<Set>
    ) {
        tvWeight.setText(weightPlan.toString())
        tvCount.setText(countPlan.toString())
        tvDate.setText(simpleDateFormat.format(date))
        tvCompletionNotFound.visibility = View.GONE
        lastCompletion.visibility = View.VISIBLE
    }

    override fun clearLastCompletion() {
        tvCompletionNotFound.visibility = View.VISIBLE
        lastCompletion.visibility = View.GONE
    }
}