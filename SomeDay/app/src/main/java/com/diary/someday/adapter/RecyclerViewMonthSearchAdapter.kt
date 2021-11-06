package com.diary.someday.adapter

import android.content.Context
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

    var data = mutableListOf<SearchMonth>()

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val month = view.findViewById<TextView>(R.id.item_month)
        val dateList = view.findViewById<RecyclerView>(R.id.item_recyclerView)
        fun bind(data: SearchMonth, context: Context) {
            month.text = data.month

            val recyclerViewDate = RecyclerViewDateSearchAdapter()
            val dataList = data.dateList
            recyclerViewDate.data = dataList

            dateList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            dateList.adapter = recyclerViewDate
            recyclerViewDate.notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_month, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], context)
    }

    override fun getItemCount() = data.size
}