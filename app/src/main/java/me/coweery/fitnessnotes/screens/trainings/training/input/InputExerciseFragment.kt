package me.coweery.fitnessnotes.screens.trainings.training.input

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import me.coweery.fitnessnotes.R
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import me.coweery.fitnessnotes.data.trainings.exercises.Exercise
import me.coweery.fitnessnotes.screens.trainings.training.TrainingContract


class InputExerciseFragment(
    private val exercisesOutput: TrainingContract.ExercisesOutput
) : DialogFragment() {

    private lateinit var etName: EditText
    private lateinit var etWeight: EditText
    private lateinit var etCount: EditText
    private lateinit var etSets: EditText
    private lateinit var btnSave: Button

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
                    weight = etWeight.text.toString().toFloat(),
                    count = etCount.text.toString().toInt(),
                    sets = etSets.text.toString().toInt()
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