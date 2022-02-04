package com.diary.someday.model.network.dto.response

data class SignInResponse(
    val code: Int,
    val secret_key: String,
    val token: String
)
