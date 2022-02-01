package com.diary.someday.viewModel.repository

import androidx.annotation.WorkerThread
import com.diary.someday.model.db.Search
import com.diary.someday.model.db.SearchDao
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

class SearchRepository(private val searchDao: SearchDao?) {

    fun getAll(): Flowable<List<Search>>? {
        return searchDao?.getAll()
    }

    fun insert(search: Search): Completable? {
        return searchDao?.insert(search)
    }

    fun delete(search: Search): Completable? {
        return searchDao?.delete(search)
    }

    fun deleteAll(): Completable? {
        return searchDao?.deleteAll()
    }
}