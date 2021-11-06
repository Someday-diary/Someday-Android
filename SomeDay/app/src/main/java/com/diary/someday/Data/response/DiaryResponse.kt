package com.diary.someday.Data.response

import com.diary.someday.Data.response.Tag
import java.util.*

data class DiaryResponse(
    val code: Int,
    val post: Posts
)

data class Posts(
    val post_id: String,
    val contents: String,
    val date: Date,
    val tags: List<Tag>
)
