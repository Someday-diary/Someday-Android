package com.yademos.someday.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Diary(
    @PrimaryKey val date : Date?,
    @ColumnInfo(name = "data") val data: String?,
    @ColumnInfo(name = "tag") val tag: String?
)