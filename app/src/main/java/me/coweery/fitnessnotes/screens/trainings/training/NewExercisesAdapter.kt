package me.coweery.fitnessnotes.screens.trainings.training

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.woxthebox.draglistview.DragItemAdapter
import me.coweery.fitnessnotes.R
import me.coweery.fitnessnotes.data.trainings.exercises.Exercise
import me.coweery.fitnessnotes.data.trainings.exercises.ExerciseWithSets
import me.coweery.fitnessnotes.data.trainings.exercises.sets.Set

class NewExercisesAdapter(
    private val context: Context,
    private val onExecrciseDelete: (Exercise) -> Unit,
    private val onSetClicked: (Exercise, Set?, Int) -> Unit,
    private val onExecrciseEdit: (Exercise) -> Unit
) : DragItemAdapter<ExerciseWithSets, NewExercisesAdapter.ViewHolder>(), ExercisesAdapter {

    private val inflater = LayoutInflater.from(context)

    init {
        itemList = mutableListOf<ExerciseWithSets>()
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun getUniqueItemId(position: Int): Long {
        return itemList[position].exercise.id!!
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        super.onBindViewHolder(viewHolder, position)
        viewHolder.adaptate(itemList[position])
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            inflater.inflate(R.layout.exercises_list_item, parent, false),
            onSetClicked,
            inflater
        ).apply {

            btnDelete.setOnClickListener {
                onExecrciseDelete(itemList[adapterPosition].exercise)
            }

            tvName.setOnClickListener {
                onExecrciseEdit(itemList[adapterPosition].exercise)
            }

            btnEdit.setOnClickListener {
                onExecrciseEdit(itemList[adapterPosition].exercise)
            }
        }
    }

    override fun addToTail(exercise: ExerciseWithSets) {

        itemList.add(exercise)
        notifyItemInserted(itemList.size - 1)
    }

    override fun update(exercise: ExerciseWithSets) {

        val oldIndex = itemList.first { it.exercise.id == exercise.exercise.id }.exercise.index
        val index = itemList.indexOfFirst { exercise.exercise.id == it.exercise.id }

        itemList[index] = exercise

        if (oldIndex == exercise.exercise.index) {
            notifyItemChanged(index)
        }
    }

    override fun deleteExercise(id: Long) {
        val pos = itemList.indexOfFirst { it.exercise.id == id }
        itemList.removeAt(pos)
        notifyItemRemoved(pos)
    }

    fun Float.beautify(): String {

        return if (this - toInt() == 0f) {
            toInt().toString()
        } else {
            toString()
        }
    }

    class ViewHolder(
        private val view: View,
        private val onSetClicked: (Exercise, Set?, Int) -> Unit,
        private val inflater: LayoutInflater
        ) : DragItemAdapter.ViewHolder(view, R.id.ll_content, true) {

        val tvName = view.findViewById<TextView>(R.id.tv_name)
        val tvCount = view.findViewById<TextView>(R.id.tv_count)
        val tvWeight = view.findViewById<TextView>(R.id.tv_weight)
        val tvSets = view.findViewById<TextView>(R.id.tv_sets)
        val llSets = view.findViewById<LinearLayout>(R.id.ll_aproaches)
        val btnDelete = view.findViewById<ImageView>(R.id.btn_delete)
        val btnEdit = view.findViewById<ImageView>(R.id.btn_edit)

        fun adaptate(exercise: ExerciseWithSets){

            tvWeight.text = exercise.exercise.weight.beautify()
            tvName.text = exercise.exercise.name
            tvCount.text = exercise.exercise.count.toString()
            tvSets.text = exercise.sets.toString()

            llSets.removeAllViews()
            exercise.sets.forEach {
                set ->
                val fractionView = inflater.inflate(R.layout.fraction, llSets, false)
                llSets.addView(fractionView)
                val layoutParams = fractionView.layoutParams as LinearLayout.LayoutParams
                layoutParams.weight = 1f
                layoutParams.width = 0
                fractionView.layoutParams = layoutParams

                fractionView.setOnClickListener {
                    onSetClicked(
                        exercise.exercise,
                        set,
                        set.index
                    )
                }

                fractionView.findViewById<TextView>(R.id.tv_weight).apply {
                    text = set.weight.beautify()
                    setTextColor(
                        ContextCompat.getColor(
                            context,
                            android.R.color.holo_green_dark
                        )
                    )
                }
                fractionView.findViewById<TextView>(R.id.tv_count).apply {
                    text = set.repsCount.toString()
                    setTextColor(
                        ContextCompat.getColor(
                            context,
                            android.R.color.holo_green_dark
                        )
                    )
                }
            }

        }

        private fun Float.beautify(): String {

            return if (this - toInt() == 0f) {
                toInt().toString()
            } else {
                toString()
            }
        }
    }


}