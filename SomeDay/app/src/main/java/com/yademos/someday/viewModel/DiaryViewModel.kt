package com.yademos.someday.viewModel

import androidx.lifecycle.*
import com.yademos.someday.db.model.Diary
import com.yademos.someday.db.repository.DiaryRepository
import kotlinx.coroutines.launch
import java.util.*

class DiaryViewModel(private val repository: DiaryRepository) : ViewModel() {

    fun insert(date: Date, data: String) = viewModelScope.launch {
        repository.insert(date, data)
    }

    fun delete(date: Date) = viewModelScope.launch {
        repository.delete(date)
    }

    fun insertTag(date: Date, tag: String) = viewModelScope.launch {
        repository.insertTag(date, tag)
    }

    fun getData(date: Date) : LiveData<String> {
        val result = MutableLiveData<String>()
        viewModelScope.launch {
            val data = repository.getData(date)
            result.postValue(data)
        }
        return result
    }
}