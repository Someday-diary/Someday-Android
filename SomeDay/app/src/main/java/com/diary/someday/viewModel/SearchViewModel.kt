package com.diary.someday.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.diary.someday.model.db.Search
import com.diary.someday.viewModel.repository.SearchRepository
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: SearchRepository): ViewModel() {
    val allSearches: LiveData<List<Search>> = repository.allSearches.asLiveData()

    fun insert(search: Search) = viewModelScope.launch {
        repository.insert(search)
    }

    fun delete(search: String) = viewModelScope.launch {
        repository.delete(search)
    }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }
}