package com.diary.someday.model.network.dto.request.diary

data class UpdateDiaryRequest(
    val contents: String,
    val tags: List<Tag>,
)
