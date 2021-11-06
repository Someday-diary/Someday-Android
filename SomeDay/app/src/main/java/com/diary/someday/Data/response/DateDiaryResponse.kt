package com.diary.someday.Data.response

import java.util.*

data class DateDiaryResponse(
    val code: Int,
    val post: DatePost
)
data class DatePost(
    val post_id: String,
    val contents: String,
    val date: Date,
    val tags: List<Tag>
)
