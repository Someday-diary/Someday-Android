package com.diary.someday.model.network.dto.response

data class DiaryListResponse(
    val code: Int?,
    val posts: List<Posts>?
)
