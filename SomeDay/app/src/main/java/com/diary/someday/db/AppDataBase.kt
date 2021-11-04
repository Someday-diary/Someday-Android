package com.diary.someday.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.diary.someday.converter.DateConverter
import com.diary.someday.db.dao.DiaryDao
import com.diary.someday.db.model.Diary
import kotlinx.coroutines.CoroutineScope

@Database(entities = [Diary::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDataBase : RoomDatabase() {
    abstract fun diaryDao(): DiaryDao

    companion object {
        @Volatile
        private var INSTANCEDIARY: AppDataBase? = null

        @Volatile
        private var INSTANCETAG: AppDataBase? = null

        fun getDiaryDb(context: Context, scope: CoroutineScope): AppDataBase {
            return INSTANCEDIARY ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDataBase::class.java,
                        "diary_database"
                ).build()
                INSTANCEDIARY = instance
                instance
            }
        }

        fun getTagDb(context: Context, scope: CoroutineScope) : AppDataBase {
            return INSTANCETAG ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "tag_database"
                ).build()
                INSTANCETAG = instance
                instance
            }
        }
    }
}

