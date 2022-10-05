package com.diary.someday.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.diary.someday.model.db.Search
import com.diary.someday.model.repository.SearchRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: SearchRepository): ViewModel() {
    private val search = MutableLiveData<List<Search>?>()

    fun searchObserve() = search

    fun getAll() {
        repository.getAll()!!.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                search.postValue(it)
            }, {
                Log.d("Error", it.message.toString())
                search.postValue(null)
            })
    }

    fun insert(search: Search) {
        repository.insert(search)!!.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    fun delete(search: Search) = viewModelScope.launch {
        repository.delete(search)!!.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()!!.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }
}