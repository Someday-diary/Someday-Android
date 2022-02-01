package com.diary.someday.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchDao {
    @Query("SELECT * FROM search_table")
    fun getAllSearches(): Flow<List<Search>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(search: Search)

    @Query("DELETE FROM search_table WHERE search = :search")
    suspend fun delete(search: String)

    @Query("DELETE FROM search_table")
    suspend fun deleteAll()

}