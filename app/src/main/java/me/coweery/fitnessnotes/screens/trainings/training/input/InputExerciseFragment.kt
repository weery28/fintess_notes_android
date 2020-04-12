package me.coweery.fitnessnotes.screens.trainings.training.input

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import me.coweery.fitnessnotes.R
import me.coweery.fitnessnotes.screens.trainings.training.TrainingContract
import me.coweery.fitnessnotes.screens.trainings.training.ifNotEmpty

class InputExerciseFragment(
    private val exercisesOutput: TrainingContract.ExercisesOutput
) : DialogFragment() {

    private lateinit var etName: EditText
    private lateinit var etWeight: EditText
    private lateinit var etCount: EditText
    private lateinit var etSets: EditText
    private lateinit var btnSave: View

    private lateinit var exercise: ExerciseInputContext

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

        exercise = arguments?.getSerializable("exercise") as ExerciseInputContext
        btnSave.setOnClickListener {
            exercisesOutput.onDataReceived(
                exercise.copy(
                    name = etName.text.toString(),
                    weight = etWeight.text.toString().ifNotEmpty({ toFloat() }, 0f),
                    count = etCount.text.toString().ifNotEmpty({ toInt() }, 0),
                    sets = etSets.text.toString().ifNotEmpty({ toInt() }, 0)
                )

            )
            dismissAllowingStateLoss()
        }
    }

    override fun onResume() {
        super.onResume()
        etWeight.setText(exercise.weight?.toString())
        etCount.setText(exercise.count?.toString())
        etName.setText(exercise.name)
        etSets.setText(exercise.sets?.toString())
    }
}