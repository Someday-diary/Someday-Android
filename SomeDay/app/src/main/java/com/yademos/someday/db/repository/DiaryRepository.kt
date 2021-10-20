package com.yademos.someday.db.repository

import androidx.annotation.WorkerThread
import com.yademos.someday.db.DiaryDataBase
import com.yademos.someday.db.dao.DiaryDao
import org.koin.java.KoinJavaComponent.inject
import java.util.*

class DiaryRepository(private val diaryDao: DiaryDao) {

    @WorkerThread
    suspend fun insert(date: Date, data: String){
        diaryDao.insertDiary(date, data)
    }

    @WorkerThread
    suspend fun delete(date: Date){
        diaryDao.deleteDiary(date)
    }

    @WorkerThread
    suspend fun insertTag(date: Date, tag: String){
        diaryDao.insertTag(date, tag)
    }
}