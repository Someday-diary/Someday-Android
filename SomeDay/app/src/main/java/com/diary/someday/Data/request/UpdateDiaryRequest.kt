package com.diary.someday.Data.request

import java.util.*

data class UpdateDiaryRequest(
    val id: String,
    val contents: String,
    val tag: List<Tag>,
)
