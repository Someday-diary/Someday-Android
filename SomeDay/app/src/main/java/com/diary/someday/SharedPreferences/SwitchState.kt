package com.yademos.someday.SharedPreferences

import android.content.Context

class SwitchState(context: Context) {
    private val sharedPreferences =
        context.getSharedPreferences("SwitchState", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    fun cancelNav() {
        editor.putBoolean("cancelNav", false)
        editor.commit()
    }

    fun getCanecelNav(): Boolean {
        return sharedPreferences.getBoolean("cancelNav", true)
    }
    fun delete(){
        editor.remove("cancelNav")
        editor.commit()
    }

    fun settingAll() {
        editor.putBoolean("settingAll", true)
        editor.commit()
    }

    fun getSettingAll(): Boolean {
        return sharedPreferences.getBoolean("settingAll", false)
    }

}