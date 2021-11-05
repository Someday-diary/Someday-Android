package com.diary.someday.SharedPreferences

import android.content.Context

class SignInCheck(context: Context) {
    private val sharedPreferences =
        context.getSharedPreferences("SignInCheck", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    fun signIn() {
        editor.putBoolean("check", true)
        editor.commit()
    }

    fun deleteAll() {
        editor.clear()
        editor.commit()
    }

    fun checkSignIn(): Boolean {
        return sharedPreferences.getBoolean("check", false)
    }
}