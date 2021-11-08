package com.diary.someday.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.diary.someday.Data.SearchDate
import com.diary.someday.R

class RecyclerViewDateSearchAdapter : RecyclerView.Adapter<RecyclerViewDateSearchAdapter.ViewHolder>() {

    private val data = mutableListOf<SearchDate>()

    fun setData(data: List<SearchDate>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val day = view.findViewById<TextView>(R.id.item_date)
        val content = view.findViewById<TextView>(R.id.item_content)
        val tag = view.findViewById<TextView>(R.id.item_tag)

        fun bind(data: SearchDate) {
            day.text = data.day.toString()
            content.text = data.content

            for (i in data.tag!!) {
                tag.append("#")
                tag.append(i.tag_name + " ")
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_date, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size
}