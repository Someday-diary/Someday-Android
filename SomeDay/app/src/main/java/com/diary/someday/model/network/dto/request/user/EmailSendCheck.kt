package com.diary.someday.model.network.dto.request.user

data class EmailSendCheck(
    var email: String,
    var code: String
) {
}