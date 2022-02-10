package com.diary.someday.view.adapter.data

import com.diary.someday.model.network.dto.response.Tag

data class SearchDate(
    val day: String?,
    val content: String?,
    val tag: List<Tag>?
)
