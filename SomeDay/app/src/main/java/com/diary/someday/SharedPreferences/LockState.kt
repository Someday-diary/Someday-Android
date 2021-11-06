package com.diary.someday.SharedPreferences

import android.content.Context

class LockState(context: Context) {
    private val sharedPreferences =
        context.getSharedPreferences("LockState", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    fun done(){
        editor.putBoolean("done",true)
        editor.commit()
    }
    fun getState():Boolean{
        return sharedPreferences.getBoolean("done",false)
    }

    fun delete(){
        editor.remove("done")
        editor.commit()
    }

    fun lockMode(mode:Int){
        editor.putInt("lockMode",mode)
        editor.commit()
    }

    fun getLockMode():Int{
        return sharedPreferences.getInt("lockMode",-1)
    }

}