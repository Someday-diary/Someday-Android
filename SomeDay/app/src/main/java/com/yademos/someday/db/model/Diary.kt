package com.yademos.someday.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Diary(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "contents") val contents: String?,
    @ColumnInfo(name = "created_at") val created_at: Date?
)