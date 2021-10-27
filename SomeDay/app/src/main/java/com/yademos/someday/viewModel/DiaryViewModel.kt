package com.yademos.someday.viewModel

import androidx.lifecycle.*
import com.yademos.someday.db.model.Diary
import com.yademos.someday.db.repository.DiaryRepository
import kotlinx.coroutines.launch
import java.util.*

class DiaryViewModel(private val repository: DiaryRepository) : ViewModel() {

    fun insertDiary(diary: Diary) = viewModelScope.launch {
        repository.insertDiary(diary)
    }

    fun deleteDiary(create_at: Date) = viewModelScope.launch {
        repository.deleteDiary(create_at)
    }

    fun getDiary(getDiary: Date) : LiveData<Diary> {
        val result = MutableLiveData<Diary>()
        viewModelScope.launch {
            val data = repository.getDiary(getDiary)
            result.postValue(data)
        }
        return result
    }
}