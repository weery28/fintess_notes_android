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
import me.coweery.fitnessnotes.data.trainings.exercises.sets.Set

class NewExercisesAdapter(
    private val context: Context,
    private val onExecrciseDelete: (Exercise) -> Unit,
    private val onSetClicked: (Exercise, Set?, Int) -> Unit,
    private val onExecrciseEdit: (Exercise) -> Unit
) : DragItemAdapter<Exercise, NewExercisesAdapter.ViewHolder>(), ExercisesAdapter {

    private val sets = mutableListOf<Set>()
    private val inflater = LayoutInflater.from(context)

    init {
        itemList = mutableListOf<Exercise>()
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun getUniqueItemId(position: Int): Long {
        return itemList[position].id!!
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        super.onBindViewHolder(viewHolder, position)
        viewHolder.tvWeight.text = itemList[position].weight.beautify()
        viewHolder.tvName.text = itemList[position].name
        viewHolder.tvCount.text = itemList[position].count.toString()
        viewHolder.tvSets.text = itemList[position].sets.toString()

        if (viewHolder.llSets.childCount != itemList[position].sets || sets.isNotEmpty()) {
            viewHolder.llSets.removeAllViews()
            (0 until itemList[position].sets).forEach { setIndex ->
                val fractionView = inflater.inflate(R.layout.fraction, viewHolder.llSets, false)
                viewHolder.llSets.addView(fractionView)
                val layoutParams = fractionView.layoutParams as LinearLayout.LayoutParams
                layoutParams.weight = 1f
                layoutParams.width = 0
                fractionView.layoutParams = layoutParams
                fractionView.setOnClickListener {
                    onSetClicked(
                        itemList[position],
                        sets.firstOrNull { set ->
                            set.exerciseId == itemList[position].id && set.index == setIndex
                        },
                        setIndex
                    )
                }
                sets.firstOrNull { set ->
                    set.index == setIndex && set.exerciseId == itemList[position].id!!
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

        return ViewHolder(
            inflater.inflate(R.layout.exercises_list_item, parent, false)
        ).apply {

            btnDelete.setOnClickListener {
                onExecrciseDelete(itemList[adapterPosition])
            }

            tvName.setOnClickListener {
                onExecrciseEdit(itemList[adapterPosition])
            }

            btnEdit.setOnClickListener {
                onExecrciseEdit(itemList[adapterPosition])
            }
        }
    }

    override fun addToTail(exercise: Exercise) {

        itemList.add(exercise)
        notifyItemInserted(itemList.size - 1)
    }

    override fun update(exercise: Exercise) {

        val oldIndex = itemList.first { it.id == exercise.id }.index
        val index = itemList.indexOfFirst { exercise.id == it.id }

        itemList[index] = exercise

        if (oldIndex == exercise.index) {
            notifyItemChanged(index)
        }
    }

    override fun add(set: Set) {
        sets.add(set)
        notifyItemChanged(itemList.indexOfFirst { it.id == set.exerciseId })
    }

    override fun deleteExercise(id: Long) {
        val pos = itemList.indexOfFirst { it.id == id }
        itemList.removeAt(pos)
        notifyItemRemoved(pos)
    }

    override fun deleteSet(id: Long) {
        val setIndex = sets.indexOfFirst { it.id == id }
        val exerciseIndex = itemList.indexOfFirst { it.id == sets[setIndex].exerciseId }
        sets.removeAt(setIndex)
        notifyItemChanged(exerciseIndex)
    }

    class ViewHolder(val view: View) : DragItemAdapter.ViewHolder(view, R.id.ll_content, true) {

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