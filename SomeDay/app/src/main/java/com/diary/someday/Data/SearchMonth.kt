package com.diary.someday.Data

data class SearchMonth(
    val month: String,
    val dateList: MutableList<SearchDate>
)
