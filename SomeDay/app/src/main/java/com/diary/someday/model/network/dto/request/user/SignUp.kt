package com.diary.someday.model.network.dto.request.user

data class SignUp(
    var email: String,
    var pwd: String,
    var agree: String
) {
}