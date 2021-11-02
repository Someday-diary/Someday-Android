package com.diary.someday.Data.diary

import java.util.*

data class UpdateDiary(
    val id : String,
    val tags: List<TagRequest>,
    val contents: String,
    val date: Date
)
