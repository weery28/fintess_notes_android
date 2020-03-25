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
import me.coweery.fitnessnotes.data.trainings.exercises.sets.Set
import me.coweery.fitnessnotes.screens.trainings.training.TrainingContract


class InputSetFragment(
    private val output: TrainingContract.SetsOutput
) : DialogFragment() {

    private lateinit var etWeight: EditText
    private lateinit var etCount: EditText
    private lateinit var btnSave: Button

    private lateinit var set: SetInputContext


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_set_input, container, false)
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

        etWeight = view.findViewById(R.id.et_weight)
        etCount = view.findViewById(R.id.et_count)
        btnSave = view.findViewById(R.id.btn_save)

        set = arguments?.getSerializable("set") as SetInputContext
        btnSave.setOnClickListener {
            output.onSetDataReceived(
                 set.copy(
                     weight = etWeight.text.toString().toFloat(),
                     repsCount = etCount.text.toString().toInt()
                 )
            )
            dismissAllowingStateLoss()
        }
    }

    override fun onResume() {
        super.onResume()
        etWeight.setText(set.weight?.toString())
        etCount.setText(set.repsCount?.toString())
    }
}