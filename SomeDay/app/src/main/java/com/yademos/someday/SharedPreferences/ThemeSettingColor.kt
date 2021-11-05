package com.yademos.someday.SharedPreferences

import android.content.Context

class ThemeSettingColor(context: Context) {
    private val sharedPreferences =
        context.getSharedPreferences("ThemeSettingColor", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    fun themeTypeColor(number: Int) {
        editor.putInt("type", number)
        editor.commit()
    }

    fun getThemeTypeColor(): Int {
        return sharedPreferences.getInt("type", 1)
    }
}