package com.diary.someday.model.network.dto.request.user

data class SignIn(
    // 이메일 입력값
    var email: String,
    // 페스워드 입력값
    var pwd: String
) {
}