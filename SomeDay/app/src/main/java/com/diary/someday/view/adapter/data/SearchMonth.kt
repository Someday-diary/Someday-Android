package com.diary.someday.view.adapter.data

data class SearchMonth(
    val year: String,
    val month: String,
    val dateList: MutableList<SearchDate>
)
