package com.diary.someday.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.diary.someday.R
import com.diary.someday.db.Search

class RecyclerViewRecentSearchAdapter: RecyclerView.Adapter<RecyclerViewRecentSearchAdapter.ViewHolder>() {

    private val data: MutableList<Search> = mutableListOf()

    fun setData(dataList: List<Search>) {
        data.clear()
        data.addAll(dataList)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val search: TextView = view.findViewById(R.id.item_search_content)

        fun bind(data: Search) {
            search.text = data.search
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size
}