package com.diary.someday.Data.response

import java.util.*

data class MonthDiaryResponse(
    val code: Int,
    val posts: List<MonthPost>
)

data class MonthPost(
    val post_id: String,
    val date: Date
)
