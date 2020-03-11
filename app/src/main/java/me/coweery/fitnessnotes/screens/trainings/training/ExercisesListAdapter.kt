package me.coweery.fitnessnotes.screens.trainings.training

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import me.coweery.fitnessnotes.R
import me.coweery.fitnessnotes.data.exercises.Exercise


class ExercisesListAdapter(
    private val context: Context
) : BaseAdapter() {


    public val exercises = mutableListOf<Exercise>()
    private val inflater = LayoutInflater.from(context);

    override fun getView(index: Int, convertView: View?, parent: ViewGroup): View {

        val view: View
        val viewHolder = if (convertView == null) {

            view = inflater.inflate(R.layout.exercises_list_item, parent, false)
            ViewHolder(
                view.findViewById(R.id.tv_name),
                view.findViewById(R.id.tv_count),
                view.findViewById(R.id.tv_weight),
                view.findViewById(R.id.tv_sets)
            ).apply {
                view.tag = this
            }
        } else {
            view = convertView
            convertView.tag as ViewHolder
        }

        if (exercises[index].weight - exercises[index].weight.toInt() == 0f){
            viewHolder.tvWeight.text = exercises[index].weight.toInt().toString()
        } else {
            viewHolder.tvWeight.text = exercises[index].weight.toString()
        }
        viewHolder.tvName.text = exercises[index].name
        viewHolder.tvCount.text = exercises[index].count.toString()
        viewHolder.tvSets.text = exercises[index].sets.toString()

        return view
    }

    override fun getItem(p0: Int): Any {
        return exercises[p0]
    }

    override fun getItemId(p0: Int) = p0.toLong()

    override fun getCount(): Int = exercises.size

    class ViewHolder(
        val tvName: TextView,
        val tvCount: TextView,
        val tvWeight: TextView,
        val tvSets: TextView
    )
}