package com.diary.someday.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.diary.someday.view.adapter.data.SearchDate
import com.diary.someday.R

class RecyclerViewDateSearchAdapter : RecyclerView.Adapter<RecyclerViewDateSearchAdapter.ViewHolder>() {

    private var mode: Int = 1
    private val data = mutableListOf<SearchDate>()

    fun setData(data: List<SearchDate>, mode: Int) {
        this.data.clear()
        this.data.addAll(data)
        this.mode = mode
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val day = view.findViewById<TextView>(R.id.item_date)
        val content = view.findViewById<TextView>(R.id.item_content)
        val tag = view.findViewById<TextView>(R.id.item_tag)

        fun bind(data: SearchDate, mode: Int) {
            changeModeColor(mode)
            day.text = data.day.toString()
            content.text = data.content

            for (i in data.tag!!) {
                tag.append("#")
                tag.append(i.tag_name + " ")
            }

        }

        private fun changeModeColor(number: Int){
            when (number) {
                1 -> {
                    day.background = ContextCompat.getDrawable(
                        itemView.context,
                        R.drawable.circle_selected_green
                    )
                    tag.setTextColor(ContextCompat.getColor(itemView.context, R.color.green3))
                }
                2 -> {
                    day.background = ContextCompat.getDrawable(
                        itemView.context,
                        R.drawable.circle_selected_blue
                    )
                    tag.setTextColor(ContextCompat.getColor(itemView.context, R.color.blue3))
                }
                3 -> {
                    day.background = ContextCompat.getDrawable(
                        itemView.context,
                        R.drawable.circle_selected_purple
                    )
                    tag.setTextColor(ContextCompat.getColor(itemView.context, R.color.purple3 ))
                }
                4 -> {
                    day.background = ContextCompat.getDrawable(
                        itemView.context,
                        R.drawable.circle_selected_yellow
                    )
                    tag.setTextColor(ContextCompat.getColor(itemView.context, R.color.yellow3))
                }
                5 -> {
                    day.background = ContextCompat.getDrawable(
                        itemView.context,
                        R.drawable.circle_selected_red
                    )
                    tag.setTextColor(ContextCompat.getColor(itemView.context, R.color.red3))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_date, parent, false)

        val layoutParams = view.layoutParams

        with(layoutParams) {
            width = ViewGroup.LayoutParams.MATCH_PARENT
            height = ViewGroup.LayoutParams.WRAP_CONTENT
        }
        view.layoutParams = layoutParams

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("Adapter", "onBindViewHolder:")
        holder.bind(data[position], mode)
    }

    override fun getItemCount() = data.size


}