package com.yademos.someday.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yademos.someday.db.DiaryDataBase
import com.yademos.someday.db.model.Diary
import java.util.*

class DiaryViewModel(application: Application): AndroidViewModel(application) {

//    private val context = getApplication<Application>().applicationContext

    private val db = DiaryDataBase.getAppDatabase(application)!!
    private val dataLiveData: MutableLiveData<Diary> = MutableLiveData()

    fun getDataLiveDataObserver() : MutableLiveData<Diary> {
        return dataLiveData
    }

    fun sendData(date: Date, data: String) {
        db.diaryDao().insertDiary(date, data)
    }

    fun setData(date: Date) : List<Diary>{
        return db.diaryDao().getDiary(date)
    }

    fun deleteData(date: Date) {
        db.diaryDao().deleteDiary(date)
    }
}