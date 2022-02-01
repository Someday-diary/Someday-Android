package com.diary.someday.model.network.dto.request.diary

data class DiaryRequest(
    val tags: List<Tag>,
    val contents: String,
    val date: String,
    val id: String
)
