package me.coweery.fitnessnotes.screens.trainings.training.input

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import me.coweery.fitnessnotes.R
import me.coweery.fitnessnotes.screens.trainings.training.TrainingContract
import me.coweery.fitnessnotes.screens.trainings.training.ifNotEmpty

class InputSetFragment(
    private val output: TrainingContract.SetsOutput
) : DialogFragment() {

    private lateinit var etWeight: EditText
    private lateinit var etCount: EditText
    private lateinit var btnSave: View
    private lateinit var btnDelete: View

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
        btnDelete = view.findViewById(R.id.btn_delete)

        etWeight.setOnFocusChangeListener { _, b ->
            if (b) {
                etWeight.setText("")
            }
        }
        etCount.setOnFocusChangeListener { _, b ->
            if (b) {
                etCount.setText("")
            }
        }
        set = arguments?.getSerializable("set") as SetInputContext
        btnSave.setOnClickListener {
            output.onSetDataReceived(
                set.copy(
                    weight = etWeight.text.toString().ifNotEmpty({ toFloat() }, 0f),
                    repsCount = etCount.text.toString().ifNotEmpty({ toInt() }, 0)
                )
            )
            dismissAllowingStateLoss()
        }

        btnDelete.setOnClickListener {
            output.onSetDeleted(set.id)
            dismissAllowingStateLoss()
        }
    }

    override fun onResume() {
        super.onResume()
        etWeight.setText(set.weight?.toString())
        etCount.setText(set.repsCount?.toString())
    }
}