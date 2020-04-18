package me.coweery.fitnessnotes.screens.trainings.training

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.woxthebox.draglistview.DragItemAdapter
import com.woxthebox.draglistview.DragListView
import me.coweery.fitnessnotes.R
import me.coweery.fitnessnotes.data.trainings.exercises.Exercise
import me.coweery.fitnessnotes.data.trainings.exercises.sets.Set

class NewExercisesAdapter (
    private val context: Context,
    private val onExecrciseDelete: (Exercise) -> Unit,
    private val onSetClicked: (Exercise, Set?, Int) -> Unit,
    private val onExecrciseEdit: (Exercise) -> Unit
) : DragItemAdapter<Exercise, NewExercisesAdapter.ViewHolder>(), ExercisesAdapter {

    private val exercises = mutableListOf<Exercise>()
    private val sets = mutableListOf<Set>()
    private val inflater = LayoutInflater.from(context)


    init {
        itemList = exercises
    }

    override fun getItemCount(): Int {
        return exercises.size
    }

    override fun getUniqueItemId(position: Int): Long {
        return exercises[position].id!!
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        super.onBindViewHolder(viewHolder, position)
        viewHolder.tvWeight.text = exercises[position].weight.beautify()
        viewHolder.tvName.text = exercises[position].name
        viewHolder.tvCount.text = exercises[position].count.toString()
        viewHolder.tvSets.text = exercises[position].sets.toString()

        viewHolder.btnDelete.setOnClickListener {
            onExecrciseDelete(exercises[position])
        }

        viewHolder.tvName.setOnClickListener {
            onExecrciseEdit(exercises[position])
        }
        viewHolder.btnEdit.setOnClickListener {
            onExecrciseEdit(exercises[position])
        }

        if (viewHolder.llSets.childCount != exercises[position].sets || sets.isNotEmpty()) {
            viewHolder.llSets.removeAllViews()
            (0 until exercises[position].sets!!).forEach { setIndex ->
                val fractionView = inflater.inflate(R.layout.fraction, viewHolder.llSets, false)
                viewHolder.llSets.addView(fractionView)
                val layoutParams = fractionView.layoutParams as LinearLayout.LayoutParams
                layoutParams.weight = 1f
                layoutParams.width = 0
                fractionView.layoutParams = layoutParams
                fractionView.setOnClickListener {
                    onSetClicked(
                        exercises[position],
                        sets.firstOrNull { set ->
                            set.exerciseId == exercises[position].id && set.index == setIndex
                        },
                        setIndex
                    )
                }
                sets.firstOrNull { set ->
                    set.index == setIndex && set.exerciseId == exercises[position].id!!
                }
                    ?.let {
                        fractionView.findViewById<TextView>(R.id.tv_weight).apply {
                            text = it.weight.beautify()
                            setTextColor(
                                ContextCompat.getColor(
                                    context,
                                    android.R.color.holo_green_dark
                                )
                            )
                        }
                        fractionView.findViewById<TextView>(R.id.tv_count).apply {
                            text = it.repsCount.toString()
                            setTextColor(
                                ContextCompat.getColor(
                                    context,
                                    android.R.color.holo_green_dark
                                )
                            )
                        }
                    }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.exercises_list_item, parent, false))
    }


    override fun add(exercise: Exercise) {
        val index = exercises.indexOfFirst { it.id == exercise.id }
        if (index != -1) {
            exercises.removeAt(index)
        }
        exercises.add(exercise)
        exercises.sortBy { it.id }
        notifyDataSetChanged()    }

    override fun add(set: Set) {
        sets.add(set)
        notifyDataSetChanged()
    }

    override fun deleteExercise(id: Long) {
        exercises.removeAt(exercises.indexOfFirst { it.id == id })
        notifyDataSetChanged()    }

    override fun deleteSet(id: Long) {
        sets.removeAt(sets.indexOfFirst { it.id == id })
        notifyDataSetChanged()
    }

    class ViewHolder(val view : View) : DragItemAdapter.ViewHolder(view, R.id.ll_content, true) {

        val tvName = view.findViewById<TextView>(R.id.tv_name)
        val tvCount = view.findViewById<TextView>(R.id.tv_count)
        val tvWeight = view.findViewById<TextView>(R.id.tv_weight)
        val tvSets = view.findViewById<TextView>(R.id.tv_sets)
        val llSets = view.findViewById<LinearLayout>(R.id.ll_aproaches)
        val btnDelete = view.findViewById<ImageView>(R.id.btn_delete)
        val btnEdit = view.findViewById<ImageView>(R.id.btn_edit)
    }

    private fun Float.beautify(): String {

        return if (this - toInt() == 0f) {
            toInt().toString()
        } else {
            toString()
        }
    }
}