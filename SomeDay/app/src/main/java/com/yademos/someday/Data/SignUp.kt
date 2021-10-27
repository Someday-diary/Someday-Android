package com.yademos.someday.Data

import com.yademos.someday.Enum.AgreeType

data class SignUp(
    var email: String,
    var pwd: String,
    var agree: String
) {
}