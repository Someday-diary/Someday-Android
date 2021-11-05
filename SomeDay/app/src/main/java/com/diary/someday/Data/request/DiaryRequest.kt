package com.diary.someday.Data.request

import java.util.*

data class DiaryRequest(
    val tags: List<Tag>,
    val contents: String,
    val date: String,
    val id: String
)
