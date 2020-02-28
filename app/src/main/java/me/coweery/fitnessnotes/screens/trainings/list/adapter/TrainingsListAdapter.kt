package me.coweery.fitnessnotes.screens.trainings.list.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.TextView
import me.coweery.fitnessnotes.R
import me.coweery.fitnessnotes.data.trainings.Training

class TrainingsListAdapter(
    private val context: Context,
    private val trainings: List<Training>
) : BaseAdapter() {

    private val inflater = LayoutInflater.from(context);

    override fun getView(index: Int, convertView: View?, parent: ViewGroup): View {

        val view: View
        val viewHolder = if (convertView == null) {

            view = inflater.inflate(R.layout.trainings_list_item, parent, false)
            ViewHolder(
                trainings[index].id,
                view.findViewById(R.id.tv_name),
                view.findViewById(R.id.cb_is_complete)
            ).apply {
                view.tag = this
            }
        } else {
            view = convertView
            convertView.tag as ViewHolder
        }

        viewHolder.tvName.text = trainings[index].name
        viewHolder.cbIsComplete.isChecked = trainings[index].isComplete

        return view
    }

    override fun getItem(p0: Int): Any {
        return trainings[p0]
    }

    override fun getItemId(p0: Int) = p0.toLong()

    override fun getCount(): Int = trainings.size

    class ViewHolder(

        val id: Long,
        val tvName: TextView,
        val cbIsComplete: CheckBox
    )
}