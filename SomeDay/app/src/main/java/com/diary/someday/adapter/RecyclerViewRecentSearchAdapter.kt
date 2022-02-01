package com.diary.someday.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.diary.someday.R
import com.diary.someday.model.db.Search
import com.diary.someday.view.adapter.listener.OnItemClickListener

class RecyclerViewRecentSearchAdapter: RecyclerView.Adapter<RecyclerViewRecentSearchAdapter.ViewHolder>() {

    private lateinit var mListener: OnItemClickListener
    private val data: MutableList<Search> = mutableListOf()
    private var searches: String = ""

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        mListener = object: OnItemClickListener {
            override fun onClick(id: Int) {
                listener(id)
            }
        }
    }

    fun getData() = searches

    fun setData(dataList: List<Search>) {
        data.clear()
        data.addAll(dataList)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val search: TextView = view.findViewById(R.id.item_search_content)
        val delete: AppCompatButton = view.findViewById(R.id.item_search_delete)

        fun bind(data: Search) {
            search.text = data.search
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
        holder.delete.setOnClickListener {
            searches = data[position].search
            mListener.onClick(1)
        }
        holder.search.setOnClickListener {
            searches = data[position].search
            mListener.onClick(2)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size
}