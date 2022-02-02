package com.diary.someday.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Search::class], version = 1)
abstract class SearchDatabase: RoomDatabase() {
    abstract fun searchDao(): SearchDao

    companion object {
        private var INSTANCE: SearchDatabase ?= null

        @Synchronized
        fun getDatabase(context: Context): SearchDatabase? {
            if (INSTANCE == null) {
                synchronized(SearchDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        SearchDatabase::class.java,
                        "search_database"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}