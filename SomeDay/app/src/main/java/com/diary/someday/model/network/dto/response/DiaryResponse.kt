package com.diary.someday.model.network.dto.response

data class DiaryResponse(
    val code: Int,
    val post: Posts
)

data class Posts(
    val post_id: String,
    val contents: String,
    val date: String,
    val tags: List<Tag>
)
