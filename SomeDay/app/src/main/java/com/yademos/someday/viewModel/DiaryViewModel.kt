package com.yademos.someday.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.yademos.someday.Constants.API
import com.yademos.someday.Data.Code
import com.yademos.someday.Data.diary.Diaries
import com.yademos.someday.Data.diary.DiaryRequest
import com.yademos.someday.Data.diary.TagRequest
import com.yademos.someday.Data.diary.UpdateDiaryRequest
import com.yademos.someday.Retrofit.DiaryService
import com.yademos.someday.Retrofit.RetrofitClient
import com.yademos.someday.db.model.Diary
import com.yademos.someday.db.repository.DiaryRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class DiaryViewModel(private val repository: DiaryRepository) : ViewModel() {

    val diaryListLiveData = MutableLiveData<List<DiaryRequest>?>()
    val diaryLiveData = MutableLiveData<Diaries?>()
    val code = MutableLiveData<Code>()

    private val retrofit: DiaryService? =
        RetrofitClient.getClient(API.BASE_URL)?.create(DiaryService::class.java)


    fun insertDiary(diary: Diary) = viewModelScope.launch {
        repository.insertDiary(diary)
    }

    fun deleteDiary(created_at: Date) = viewModelScope.launch {
        repository.deleteDiary(created_at)
    }

    fun getDiary(created_at: Date) : LiveData<Diary> {
        val result = MutableLiveData<Diary>()
        viewModelScope.launch {
            val data = repository.getDiary(created_at)
            result.postValue(data)
        }
        return result
    }

    fun callCreateDiary(requestDiary: DiaryRequest) {
        retrofit?.createDiary(requestDiary)?.enqueue(object : Callback<Code> {
            override fun onResponse(call: Call<Code>, response: Response<Code>) {
                if (response.isSuccessful) {
                    code.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<Code>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun callGetDiaryWithPostId(post_id: String) {
        retrofit?.getDiaryWithPostId(post_id)?.enqueue(object : Callback<Diaries>{
            override fun onResponse(call: Call<Diaries>, response: Response<Diaries>) {
                if (response.isSuccessful) {
                    if(response.code() == 110) {
                        diaryLiveData.postValue(null)
                    }
                    diaryLiveData.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<Diaries>, t: Throwable) {
                diaryLiveData.postValue(null)
            }
        })
    }

    fun callGetDiary(tags: List<TagRequest>) {
        retrofit?.getDiary(tags)?.enqueue(object : Callback<List<DiaryRequest>>{
            override fun onResponse(
                call: Call<List<DiaryRequest>>,
                response: Response<List<DiaryRequest>>
            ) {
                if (response.isSuccessful) {
                    diaryListLiveData.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<List<DiaryRequest>>, t: Throwable) {
                diaryListLiveData.postValue(null)
            }
        })
    }

    fun callUpdateDiary(updateDiaryRequest: UpdateDiaryRequest) {
        retrofit?.updateDiary(updateDiaryRequest)?.enqueue(object : Callback<Code> {
            override fun onResponse(call: Call<Code>, response: Response<Code>) {
                code.postValue(response.body())
            }

            override fun onFailure(call: Call<Code>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun callDeleteDiary(post_id: String) {
        retrofit?.deleteDiary(post_id)?.enqueue(object : Callback<Code> {
            override fun onResponse(call: Call<Code>, response: Response<Code>) {
                code.postValue(response.body())
            }

            override fun onFailure(call: Call<Code>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}