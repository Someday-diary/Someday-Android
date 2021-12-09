package com.yademos.someday.SharedPreferences

import android.content.Context
import android.util.Log

class LockNumber(context: Context) {
    private val sharedPreferences =
        context.getSharedPreferences("LockNumber", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    fun addNumber(pwd: String) {
        editor.putString("addNumber", pwd)
        editor.commit()
    }

    fun getAddNumber(): String? = sharedPreferences.getString("addNumber", "-1")

    fun addType(type: String) {
        editor.putString("addType", type)
        editor.commit()
    }

    fun getAddType(): String? = sharedPreferences.getString("addType", "-1")

    fun done(){
        editor.putBoolean("done",true)
        editor.commit()
    }

    fun getDone():Boolean = sharedPreferences.getBoolean("done",false)

    fun delete(){
        editor.remove("done")
        editor.commit()
    }
}