package com.diary.someday.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.diary.someday.db.model.Diary
import java.util.*

@Dao
interface DiaryDao {
    @Insert
    suspend fun insertDiary(diary: Diary)

    @Query("DELETE FROM Diary WHERE created_at = :created_at")
    suspend fun deleteDiary(created_at: Date)

    @Query("SELECT * FROM Diary WHERE created_at = :created_at")
    suspend fun getDiary(created_at: Date) : Diary

}