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
    @Query("INSERT INTO diary VALUES (:date, :data, :tag)")
    suspend fun insertDiary(date: Date, data: String, tag: String)

    @Query("DELETE FROM Diary WHERE date = :date")
    suspend fun deleteDiary(date: Date)

    @Query("SELECT * FROM diary WHERE date = :date")
    suspend fun getDiary(date: Date) : Diary

}