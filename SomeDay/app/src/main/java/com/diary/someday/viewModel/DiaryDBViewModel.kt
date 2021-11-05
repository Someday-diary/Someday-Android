package com.diary.someday.viewModel

import androidx.lifecycle.*
import com.diary.someday.db.model.Diary
import com.diary.someday.db.repository.DiaryRepository
import kotlinx.coroutines.launch
import java.util.*

class DiaryDBViewModel(private val repository: DiaryRepository) : ViewModel() {

    fun insertDiary(diary: Diary) = viewModelScope.launch {
        repository.insertDiary(diary)
    }

    fun deleteDiary(created_at: Date) = viewModelScope.launch {
        repository.deleteDiary(created_at)
    }

    fun getDiary(created_at: Date): LiveData<Diary> {
        val result = MutableLiveData<Diary>()
        viewModelScope.launch {
            val data = repository.getDiary(created_at)
            result.postValue(data)
        }
        return result
    }

}