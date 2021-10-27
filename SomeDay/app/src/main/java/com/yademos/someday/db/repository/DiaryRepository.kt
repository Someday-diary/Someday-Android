package com.yademos.someday.db.repository

import androidx.annotation.WorkerThread
import com.yademos.someday.db.dao.DiaryDao
import com.yademos.someday.db.model.Diary
import java.util.*

class DiaryRepository(private val diaryDao: DiaryDao) {

    @WorkerThread
    suspend fun insert(date: Date, data: String, tag: String){
        diaryDao.insertDiary(date, data, tag)
    }

    @WorkerThread
    suspend fun delete(date: Date){
        diaryDao.deleteDiary(date)
    }

    @WorkerThread
    suspend fun insertTag(date: Date, tag: String){
        diaryDao.insertTag(date, tag)
    }

    @WorkerThread
    suspend fun getData(date: Date) : Diary {
        return diaryDao.getDiary(date)
    }
}