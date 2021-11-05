package com.diary.someday.Data.request

import java.util.*

data class UpdateDiaryRequest(
    val contents: String,
    val tags: List<Tag>,
)
