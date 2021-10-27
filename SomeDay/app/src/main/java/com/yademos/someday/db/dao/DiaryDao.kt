package com.yademos.someday.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.yademos.someday.db.model.Diary
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface DiaryDao {
    @Insert
    suspend fun insertDiary(diary: Diary)

    @Query("DELETE FROM Diary WHERE create_at = :create_at")
    suspend fun deleteDiary(create_at: Date)

    @Query("SELECT * FROM diary WHERE create_at = :create_at")
    suspend fun getDiary(create_at: Date) : Diary

}