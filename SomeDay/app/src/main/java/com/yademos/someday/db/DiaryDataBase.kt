package com.yademos.someday.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yademos.someday.db.dao.DiaryDao
import com.yademos.someday.db.model.Diary

@Database(entities = [Diary::class], version = 1)
abstract class DiaryDataBase : RoomDatabase() {

    private lateinit var INSTANCE: DiaryDataBase
    abstract fun diaryDao(): DiaryDao

    fun getAppDatabase(context: Context?): DiaryDataBase? {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                context!!,
                DiaryDataBase::class.java,
                "todo-db"
            ).build()
        }
        return INSTANCE
    }
}