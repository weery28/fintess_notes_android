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
    private val onExecrciseDelete: (Long) -> Unit,
    private val onSetClicked: (Exercise, Set?, Int) -> Unit,
    private val onExecrciseEdit: (Exercise) -> Unit
) : DragItemAdapter<NewExercisesAdapter.ListItem, NewExercisesAdapter.ViewHolder>(),
    ExercisesAdapter {

    private val inflater = LayoutInflater.from(context)

    init {
        itemList = mutableListOf<ListItem>()
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
                onExecrciseDelete(itemList[adapterPosition].exercise.id!!)
            }

            tvName.setOnClickListener {
                onExecrciseEdit(itemList[adapterPosition].exercise)
            }

            btnEdit.setOnClickListener {
                onExecrciseEdit(itemList[adapterPosition].exercise)
            }
        }
    }

    override fun update(exercise: Exercise) {

        val index = itemList.indexOfFirst { it.exercise.id == exercise.id }

        if (index == -1) {
            itemList.add(ListItem(exercise.copy(index = itemList.size), mutableListOf()))
            notifyItemInserted(itemList.size - 1)
        } else {
            itemList[index].exercise = exercise
            notifyItemChanged(index)
        }
    }

    override fun update(set: Set) {

        val index = itemList.indexOfFirst { it.exercise.id == set.exerciseId }
        val setIndex = itemList[index].sets.indexOfFirst { it.id == set.id }

        if (setIndex == -1) {
            itemList[index].sets.add(set)
            notifyItemChanged(index)
        } else {
            itemList[index].sets.removeAt(setIndex)
            itemList[index].sets.add(set)
            notifyItemChanged(index)
        }
    }

    override fun deleteSet(setId: Long) {

        var setIndex: Int? = null
        val index = itemList.indexOfFirst {

            val deletedSet = it.sets.indexOfFirst { it.id == setId }
            setIndex = deletedSet
            deletedSet != -1
        }

        itemList[index].sets.removeAt(setIndex!!)
        notifyItemChanged(index)
    }

    override fun deleteExercise(id: Long) {

        val index = itemList.indexOfFirst { it.exercise.id == id }
        itemList.removeAt(index)
        notifyItemRemoved(index)
    }

    override fun actualizeIndexes() {

        itemList.forEachIndexed { index, listItem ->
            listItem.exercise = listItem.exercise.copy(index = index)
        }
    }

    class ListItem(
        var exercise: Exercise,
        val sets: MutableList<Set>
    )

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

        fun adaptate(listItem: ListItem) {

            tvWeight.text = listItem.exercise.weight.beautify()
            tvName.text = listItem.exercise.name
            tvCount.text = listItem.exercise.count.toString()
            tvSets.text = listItem.exercise.sets.toString()

            llSets.removeAllViews()
            (0 until listItem.exercise.sets).forEach { i ->

                val set = listItem.sets.firstOrNull { it.index == i }
                val fractionView = inflater.inflate(R.layout.fraction, llSets, false)
                llSets.addView(fractionView)
                val layoutParams = fractionView.layoutParams as LinearLayout.LayoutParams
                layoutParams.weight = 1f
                layoutParams.width = 0

                fractionView.layoutParams = layoutParams
                fractionView.setOnClickListener {
                    onSetClicked(
                        listItem.exercise,
                        null,
                        i
                    )
                }

                if (set != null) {
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