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

    fun delete() {
        editor.remove("cancelNav")
        editor.commit()
    }

    fun deleteAll() {
        editor.clear()
        editor.commit()
    }

    fun settingAll() {
        editor.putBoolean("settingAll", true)
        editor.commit()
    }

    fun getSettingAll(): Boolean {
        return sharedPreferences.getBoolean("settingAll", false)
    }

    fun bioSwitchOn(){
        editor.putBoolean("bioSwitchOn",true)
        editor.commit()
    }

    fun bioSwitchOff(){
        editor.putBoolean("bioSwitchOn",false)
        editor.commit()
    }

    fun getBioSwitch() : Boolean = sharedPreferences.getBoolean("bioSwitchOn",false)

}