package com.yademos.someday.SharedPreferences

import android.content.Context
import android.util.Log

class LockNumber(context: Context) {
    private val sharedPreferences =
        context.getSharedPreferences("LockNumber", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    fun addNumber(pwd:String){
        editor.putString("addNumber",pwd)
        editor.commit()
    }

    fun getAddNumber(): String? = sharedPreferences.getString("addNumber","-1")
}