package com.diary.someday.viewModel.repository

import androidx.annotation.WorkerThread
import com.diary.someday.model.db.Search
import com.diary.someday.model.db.SearchDao

class SearchRepository(private val searchDao: SearchDao) {
    val allSearches = searchDao.getAllSearches()

    @WorkerThread
    suspend fun insert(search: Search) {
        searchDao.insert(search)
    }

    @WorkerThread
    suspend fun delete(search: String) {
        searchDao.delete(search)
    }

    @WorkerThread
    suspend fun deleteAll() {
        searchDao.deleteAll()
    }
}