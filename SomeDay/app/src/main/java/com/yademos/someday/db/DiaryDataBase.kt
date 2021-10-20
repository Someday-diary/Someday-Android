package com.yademos.someday.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.yademos.someday.converter.DateConverter
import com.yademos.someday.db.dao.DiaryDao
import com.yademos.someday.db.model.Diary
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Diary::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class DiaryDataBase : RoomDatabase() {
    abstract fun diaryDao(): DiaryDao

    companion object {
        @Volatile
        private var INSTANCE: DiaryDataBase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): DiaryDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DiaryDataBase::class.java,
                    "word_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

