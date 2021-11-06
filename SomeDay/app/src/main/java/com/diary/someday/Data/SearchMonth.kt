package com.diary.someday.Data

data class SearchMonth(
    val year: String,
    val month: String,
    val dateList: MutableList<SearchDate>
)
