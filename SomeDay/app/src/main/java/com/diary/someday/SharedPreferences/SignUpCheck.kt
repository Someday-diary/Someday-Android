package com.yademos.someday.SharedPreferences

import android.content.Context

class SignUpCheck(context: Context) {
    private val sharedPreferences =
        context.getSharedPreferences("SignUpCheck", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    fun signUp() {
        editor.putBoolean("check",true)
        editor.commit()
    }

    fun checkSignUp(): Boolean {
        return sharedPreferences.getBoolean("check",false)
    }
}