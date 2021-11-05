package com.yademos.someday.SharedPreferences

import android.content.Context

class ThemeSetting(context: Context) {
    private val sharedPreferences =
        context.getSharedPreferences("ThemeSetting", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    fun themeType(number: Int) {
        editor.putInt("type", number)
        editor.commit()
    }

    fun getThemeType(): Int {
        return sharedPreferences.getInt("type", 1)
    }
}