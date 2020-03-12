package me.coweery.fitnessnotes.screens.trainings.training

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import me.coweery.fitnessnotes.R
import me.coweery.fitnessnotes.data.trainings.exercises.Exercise


class ExercisesListAdapter(
    private val context: Context,
    private val onExecrciseDelete : (Exercise) -> Unit,
    private val onSetClicked : (Exercise, Int) -> Unit
) : BaseAdapter() {


    public val exercises = mutableListOf<Exercise>()
    private val inflater = LayoutInflater.from(context)
    private var isActiveState = false

    override fun getView(index: Int, convertView: View?, parent: ViewGroup): View {

        val view: View
        val viewHolder = if (convertView == null) {

            view = inflater.inflate(R.layout.exercises_list_item, parent, false)
            ViewHolder(
                view.findViewById(R.id.tv_name),
                view.findViewById(R.id.tv_count),
                view.findViewById(R.id.tv_weight),
                view.findViewById(R.id.tv_sets),
                view.findViewById(R.id.ll_aproaches),
                view.findViewById(R.id.btn_delete)
            ).apply {
                view.tag = this
            }
        } else {
            view = convertView
            convertView.tag as ViewHolder
        }

        if (exercises[index].weight - exercises[index].weight.toInt() == 0f) {
            viewHolder.tvWeight.text = exercises[index].weight.toInt().toString()
        } else {
            viewHolder.tvWeight.text = exercises[index].weight.toString()
        }
        viewHolder.tvName.text = exercises[index].name
        viewHolder.tvCount.text = exercises[index].count.toString()
        viewHolder.tvSets.text = exercises[index].sets.toString()

        viewHolder.btnDelete.setOnClickListener {
            onExecrciseDelete(exercises[index])
        }

        if (isActiveState) {
            if (viewHolder.llSets.childCount != exercises[index].sets){
                viewHolder.llSets.removeAllViews()
                (0 until exercises[index].sets).forEach {
                    val fractionView = inflater.inflate(R.layout.fraction, viewHolder.llSets, false)
                    viewHolder.llSets.addView(fractionView)
                    val layoutParams = fractionView.layoutParams as LinearLayout.LayoutParams
                    layoutParams.weight = 1f
                    layoutParams.width = 0
                    fractionView.layoutParams = layoutParams
                    fractionView.setOnClickListener { _ ->
                        onSetClicked(exercises[index], it)
                    }
                }
            }
        } else {
            viewHolder.llSets.removeAllViews()
        }

        return view
    }

    override fun getItem(i: Int) = exercises[i]

    override fun getItemId(i: Int) = exercises[i].id!!

    override fun getCount(): Int = exercises.size

    fun add(exercise: Exercise) {
        exercises.add(exercise)
        exercises.sortBy { it.id }
        notifyDataSetChanged()
    }

    fun delete(id : Long) {
        exercises.removeAt(exercises.indexOfFirst { it.id == id })
        notifyDataSetChanged()
    }

    fun toActiveState() {
        isActiveState = true
        notifyDataSetChanged()
    }

    fun toStoppedState() {
        isActiveState = false
        notifyDataSetChanged()
    }

    private class ViewHolder(
        val tvName: TextView,
        val tvCount: TextView,
        val tvWeight: TextView,
        val tvSets: TextView,
        val llSets: LinearLayout,
        val btnDelete: ImageView
    )
}