package com.diary.someday.viewModel

import androidx.lifecycle.*
import com.diary.someday.Constants.API
import com.diary.someday.Data.response.Code
import com.diary.someday.Data.request.DiaryRequest
import com.diary.someday.Data.request.Tag
import com.diary.someday.Data.request.UpdateDiaryRequest
import com.diary.someday.Data.response.DiaryResponse
import com.diary.someday.Data.response.DateDiaryResponse
import com.diary.someday.Data.response.MonthDiaryResponse
import com.diary.someday.Retrofit.DiaryService
import com.diary.someday.Retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DiaryViewModel() : ViewModel() {

    val diaryLiveData = MutableLiveData<DiaryResponse?>()
    val diaryListLiveData = MutableLiveData<List<DiaryResponse>?>()
    val monthDiaryLiveData = MutableLiveData<MonthDiaryResponse?>()
    val dateDiaryLiveData = MutableLiveData<DateDiaryResponse?>()
    val code = MutableLiveData<Code?>()

    private val retrofit: DiaryService? =
        RetrofitClient.getClient(API.BASE_URL)?.create(DiaryService::class.java)

    fun callCreateDiary(diaries: DiaryRequest) {
        retrofit?.createDiary(diaries)?.enqueue(object : Callback<Code> {
            override fun onResponse(call: Call<Code>, response: Response<Code>) {
                if (response.isSuccessful) {
                    code.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<Code>, t: Throwable) {
                code.postValue(null)
            }
        })
    }

    fun callGetDiaryWithPostId(post_id: String) {
        retrofit?.getDiaryWithPostId(post_id)?.enqueue(object : Callback<DiaryResponse> {
            override fun onResponse(call: Call<DiaryResponse>, response: Response<DiaryResponse>) {
                if (response.isSuccessful) {
                    if (response.code() == 110) {
                        diaryLiveData.postValue(null)
                    }
                    diaryLiveData.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<DiaryResponse>, t: Throwable) {
                diaryLiveData.postValue(null)
            }
        })
    }

    fun callGetDiary() {
        retrofit?.getDiary()?.enqueue(object : Callback<List<DiaryResponse>> {
            override fun onResponse(
                call: Call<List<DiaryResponse>>,
                response: Response<List<DiaryResponse>>
            ) {
                if (response.isSuccessful) {
                    diaryListLiveData.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<List<DiaryResponse>>, t: Throwable) {
                diaryListLiveData.postValue(null)
            }
        })
    }

    fun callGetDiaryWithTag(tags: List<Tag>) {
        retrofit?.getDiary(tags)?.enqueue(object : Callback<List<DiaryResponse>> {
            override fun onResponse(
                call: Call<List<DiaryResponse>>,
                response: Response<List<DiaryResponse>>
            ) {
                if (response.isSuccessful) {
                    diaryListLiveData.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<List<DiaryResponse>>, t: Throwable) {
                diaryListLiveData.postValue(null)
            }
        })
    }

    fun callGetMonthDiary(year: Int, month: Int) {
        retrofit?.getMonthDiary(year, month)?.enqueue(object : Callback<MonthDiaryResponse>{
            override fun onResponse(call: Call<MonthDiaryResponse>, response: Response<MonthDiaryResponse>) {
                if (response.isSuccessful) {
                    monthDiaryLiveData.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<MonthDiaryResponse>, t: Throwable) {
                monthDiaryLiveData.postValue(null)
            }
        })
    }

    fun callGetDateDiary(year: Int, month: Int, day: Int) {
        retrofit?.getDateDiary(year, month, day)?.enqueue(object : Callback<DateDiaryResponse> {
            override fun onResponse(
                call: Call<DateDiaryResponse>,
                response: Response<DateDiaryResponse>
            ) {
                if (response.isSuccessful) {
                    dateDiaryLiveData.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<DateDiaryResponse>, t: Throwable) {
                dateDiaryLiveData.postValue(null)
            }
        })
    }

    fun callUpdateDiary(updateDiaryRequest: UpdateDiaryRequest) {
        retrofit?.updateDiary(updateDiaryRequest)?.enqueue(object : Callback<Code> {
            override fun onResponse(call: Call<Code>, response: Response<Code>) {
                code.postValue(response.body())
            }

            override fun onFailure(call: Call<Code>, t: Throwable) {
                code.postValue(null)
            }
        })
    }

    fun callDeleteDiary(post_id: String) {
        retrofit?.deleteDiary(post_id)?.enqueue(object : Callback<Code> {
            override fun onResponse(call: Call<Code>, response: Response<Code>) {
                code.postValue(response.body())
            }

            override fun onFailure(call: Call<Code>, t: Throwable) {
                code.postValue(null)
            }
        })
    }
}