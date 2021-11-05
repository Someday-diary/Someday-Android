package com.yademos.someday.SharedPreferences

import android.content.Context
import android.util.Log

class LockNumberCheck(context: Context) {
    private val sharedPreferences =
        context.getSharedPreferences("LockNumberCheck", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    fun addNumber(num: Int) {
        val num1 = sharedPreferences.getInt("1", -1)
        val num2 = sharedPreferences.getInt("2", -1)
        val num3 = sharedPreferences.getInt("3", -1)
        val num4 = sharedPreferences.getInt("4", -1)
        if (num3 != -1) {
            Log.d("TAG", "addNumber: if문 1")
            editor.putInt("4", num)
            editor.commit()
            selectAll()
        } else if (num2 != -1) {
            Log.d("TAG", "addNumber: if문 2")
            Log.d("TAG", "addNumber: if문 4 before ${num3}\n")
            editor.putInt("3", num)
            editor.commit()
            Log.d("TAG", "addNumber: if문 4 before ${num3}\n")
            circleChangeNumber(3)
        } else if (num1 != -1) {
            Log.d("TAG", "addNumber: if문 3")
            Log.d("TAG", "addNumber: if문 4 before ${num2}\n")
            editor.putInt("2", num)
            editor.commit()
            Log.d("TAG", "addNumber: if문 4 after ${num2}\n")
            circleChangeNumber(2)
        } else {
            Log.d("TAG", "addNumber: if문 4")
            Log.d("TAG", "addNumber: if문 4 before ${num1}\n")
            editor.putInt("1", num)
            editor.commit()
            Log.d("TAG", "addNumber: if문 4 after ${num1}")
            circleChangeNumber(1)
        }
    }

    fun removeNumber() {
        val num1 = sharedPreferences.getInt("1", -1)
        val num2 = sharedPreferences.getInt("2", -1)
        val num3 = sharedPreferences.getInt("3", -1)
        val num4 = sharedPreferences.getInt("4", -1)
        if (num3 != -1) {
            Log.d("TAG", "removeNumber: if문 1")
            Log.d("TAG", "removeNumber: if문 1 ${num4}")
            editor.remove("3")
            editor.commit()
            circleChangeNumber(2)
        } else if (num2 != -1) {
            Log.d("TAG", "removeNumber: if문 2")
            Log.d("TAG", "removeNumber: if문 2 ${num3}")
            editor.remove("2")
            editor.commit()
            circleChangeNumber(1)
        } else if (num1 != -1) {
            Log.d("TAG", "removeNumber: if문 3")
            Log.d("TAG", "removeNumber: if문 3 ${num2}")
            editor.remove("1")
            editor.commit()
            circleChangeNumber(0)
        } else {
            Log.d("TAG", "removeNumber: if문 4")
            Log.d("TAG", "removeNumber: if문 4 ${num1}")
            circleChangeNumber(0)
        }
    }

    fun circleChangeNumber(num: Int) {
        Log.d("TAG", "circleChange: $num")
        editor.putInt("circleNumber", num)
        editor.commit()
    }

    fun getCircleChangeNumber(): Int = sharedPreferences.getInt("circleNumber", 0)

    fun selectAll() {
        editor.putBoolean("selectAll", true)
        editor.commit()
    }

    fun getSelectAll(): Boolean = sharedPreferences.getBoolean("selectAll", false)

    fun removeAll() {
        editor.clear()
        editor.commit()
    }

    fun getNumberCheck(): String =
        sharedPreferences.getInt("1", -1).toString() + sharedPreferences.getInt("2", -1)
            .toString() + sharedPreferences.getInt("3", -1)
            .toString() + sharedPreferences.getInt("4", -1).toString()

    fun numberCheckAll(){
        editor.putBoolean("numberCheck",true)
        editor.commit()
    }

    fun getNumberCheckAll() : Boolean = sharedPreferences.getBoolean("numberCheck",false)
}