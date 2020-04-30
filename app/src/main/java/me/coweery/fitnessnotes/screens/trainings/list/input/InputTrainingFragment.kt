package me.coweery.fitnessnotes.screens.trainings.list.input

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.DatePicker
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import me.coweery.fitnessnotes.R
import java.util.Calendar

class InputTrainingFragment : DialogFragment() {

    private lateinit var etName : EditText
    private lateinit var etDate : EditText
    private lateinit var datePicker : DatePicker


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_training_input, container, false)
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
        etDate = view.findViewById(R.id.et_date)
        datePicker = DatePicker(context)

        etDate.setOnClickListener {

        }

        val calendar = Calendar.getInstance()
        datePicker.init(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ){
                view, year, month, day ->
        }

    }

    override fun onResume() {
        super.onResume()


        etName.setText("")
        etDate.setText("")
    }
}