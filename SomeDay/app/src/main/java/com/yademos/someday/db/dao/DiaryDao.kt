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
    @Query("INSERT INTO diary VALUES (:date, :data, null)")
    suspend fun insertDiary(date: Date, data: String)

    @Query("DELETE FROM Diary WHERE date = :date")
    suspend fun deleteDiary(date: Date)

    @Query("UPDATE diary SET tag = :tag WHERE date = :date")
    suspend fun insertTag(date: Date, tag: String)
}