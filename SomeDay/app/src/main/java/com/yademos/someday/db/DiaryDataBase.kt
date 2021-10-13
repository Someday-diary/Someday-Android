package com.yademos.someday.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yademos.someday.db.dao.DiaryDao
import com.yademos.someday.db.model.Diary

@Database(entities = [Diary::class], version = 1)
abstract class DiaryDataBase : RoomDatabase() {

    abstract fun diaryDao(): DiaryDao

    companion object {
        fun getAppDatabase(context: Context?): DiaryDataBase? {

            var INSTANCE: DiaryDataBase? = null

            if (null == INSTANCE) {
                INSTANCE = Room.databaseBuilder(
                    context!!,
                    DiaryDataBase::class.java,
                    "todo-db"
                ).build()
            }
            return INSTANCE
        }
    }
}