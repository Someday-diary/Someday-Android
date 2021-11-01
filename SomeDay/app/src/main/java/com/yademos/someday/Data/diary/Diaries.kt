package com.yademos.someday.Data.diary

import java.util.*

data class Diaries(
    val tag: List<TagRequest>?,
    val contents: String?,
    val date: Date?
)