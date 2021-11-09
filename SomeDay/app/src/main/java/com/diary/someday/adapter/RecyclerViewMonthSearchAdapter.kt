package com.diary.someday.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.diary.someday.Data.SearchDate
import com.diary.someday.Data.SearchMonth
import com.diary.someday.R

class RecyclerViewMonthSearchAdapter(val context: Context): RecyclerView.Adapter<RecyclerViewMonthSearchAdapter.ViewHolder>() {

    private val data = mutableListOf<SearchMonth>()

    fun setData(data: List<SearchMonth>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val month = view.findViewById<TextView>(R.id.item_month)
        val dateList = view.findViewById<RecyclerView>(R.id.item_recyclerView)
        fun bind(data: SearchMonth, context: Context) {
            month.text = data.year + "년 " + data.month + "월"

            val recyclerViewDateAdapter = RecyclerViewDateSearchAdapter()
            dateList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            dateList.adapter = recyclerViewDateAdapter

            val dataList = data.dateList
            recyclerViewDateAdapter.setData(dataList)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_month, parent, false)

        val layoutParams = view.layoutParams

        with(layoutParams) {
            width = ViewGroup.LayoutParams.MATCH_PARENT
            height = ViewGroup.LayoutParams.WRAP_CONTENT
        }
        view.layoutParams = layoutParams

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], context)
    }

    override fun getItemCount() = data.size
}