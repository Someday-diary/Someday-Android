package com.diary.someday.model.network.dto

data class SignInResponse(
    val code: Int,
    val secret_key: String,
    val token: String
)
