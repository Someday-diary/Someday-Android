package com.yademos.someday.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.yademos.someday.db.DiaryDataBase
import com.yademos.someday.db.model.Diary
import java.util.*

class DiaryViewModel(application: Application): AndroidViewModel(application) {

//    private val context = getApplication<Application>().applicationContext

    private val db = DiaryDataBase.getAppDatabase(application)!!

    fun sendData(date: Date, data: String) {
        db.diaryDao().insertDiary(date, data)
    }

    fun setData(date: Date) : List<Diary>{
        return db.diaryDao().getDiary(date)
    }
}