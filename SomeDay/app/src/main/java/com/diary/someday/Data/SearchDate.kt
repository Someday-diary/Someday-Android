package com.diary.someday.Data

import com.diary.someday.Data.response.Tag

data class SearchDate(
    val day: String?,
    val content: String?,
    val tag: List<Tag>?
)
