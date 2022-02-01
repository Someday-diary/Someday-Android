package com.diary.someday.model.db

import androidx.room.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchDao {
    @Query("SELECT * FROM search_table")
    fun getAll(): Flowable<List<Search>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(search: Search): Completable

    @Delete
    fun delete(search: Search): Completable

    @Query("DELETE FROM search_table")
    fun deleteAll(): Completable

}