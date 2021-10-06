package com.yademos.someday.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.yademos.someday.db.model.Diary
import java.util.*

@Dao
interface DiaryDao {
    @Query("SELECT * FROM diary WHERE date = :date")
    fun getDiary(date: Date): List<Diary>

    @Query("INSERT INTO diary VALUES (:date, :data, null)")
    fun insertDiary(date: Date, data: String)

    @Query("DELETE FROM Diary WHERE date = :date")
    fun deleteDiary(date: Date)

    @Query("UPDATE diary SET tag = :tag WHERE date = :date")
    fun insertTag(tag: String, date: Date)
}