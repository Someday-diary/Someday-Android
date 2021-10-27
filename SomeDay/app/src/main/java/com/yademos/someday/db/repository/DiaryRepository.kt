package com.yademos.someday.db.repository

import androidx.annotation.WorkerThread
import com.yademos.someday.db.dao.DiaryDao
import com.yademos.someday.db.model.Diary
import java.util.*

class DiaryRepository(private val diaryDao: DiaryDao) {

    @WorkerThread
    suspend fun insertDiary(diary: Diary){
        diaryDao.insertDiary(diary)
    }

    @WorkerThread
    suspend fun deleteDiary(create_at: Date){
        diaryDao.deleteDiary(create_at)
    }


    @WorkerThread
    suspend fun getDiary(create_at: Date) : Diary {
        return diaryDao.getDiary(create_at)
    }
}