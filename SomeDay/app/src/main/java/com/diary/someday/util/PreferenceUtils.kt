package com.diary.someday.util

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

object PreferenceUtils {
    private lateinit var preferences: SharedPreferences

    fun init(context: Context) {
        preferences = context.getSharedPreferences("token", MODE_PRIVATE)
    }

    var token: String?
        get() =
            preferences.getString("token", null)
        set(value) =
            preferences.edit().putString("token", value).apply()
}