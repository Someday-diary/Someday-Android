package com.diary.someday.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_table")
data class Search(
    @PrimaryKey @ColumnInfo(name = "search") val search: String
)
