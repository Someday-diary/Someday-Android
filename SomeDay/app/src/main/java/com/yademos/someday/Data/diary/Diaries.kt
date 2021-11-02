package com.yademos.someday.Data.diary

import java.util.*

data class Diaries(
    val tags: List<Tag>?,
    val contents: String?,
    val date: Date?,
    val id: String?
)