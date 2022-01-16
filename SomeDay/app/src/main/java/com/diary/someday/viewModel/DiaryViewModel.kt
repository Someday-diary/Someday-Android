package com.diary.someday.viewModel

import androidx.lifecycle.*
import com.diary.someday.model.network.util.Constants.API
import com.diary.someday.model.network.dto.request.diary.DiaryRequest
import com.diary.someday.model.network.dto.request.diary.UpdateDiaryRequest
import com.diary.someday.model.network.dto.response.*
import com.diary.someday.model.network.dao.DiaryService
import com.diary.someday.model.network.RetrofitClient
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class DiaryViewModel() : ViewModel() {

    val diaryLiveData = MutableLiveData<DiaryResponse?>()
    val diaryListLiveData = MutableLiveData<DiaryListResponse?>()
    val monthDiaryLiveData = MutableLiveData<MonthDiaryResponse?>()
    val dateDiaryLiveData = MutableLiveData<DateDiaryResponse?>()
    val code = MutableLiveData<Code?>()

    private val retrofit: DiaryService? =
        RetrofitClient.getClient(API.BASE_URL)?.create(DiaryService::class.java)

    private val disposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    fun callCreateDiary(diaries: DiaryRequest) {
        retrofit?.createDiary(diaries)?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())?.subscribe({
            code.postValue(it.body())
        }, {
            code.postValue(null)
        }).apply { disposable.add(this) }
    }

    fun callGetDiaryWithPostId(post_id: String) {
        retrofit!!.getDiaryWithPostId(post_id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({ response ->
            if (response.isSuccessful) {
                diaryLiveData.postValue(response.body())
            } else {
                diaryLiveData.postValue(response.body())
            }
        }, {
            diaryLiveData.postValue(null)
        }).apply { disposable.add(this) }
    }

    fun callGetDiary() {
        retrofit!!.getDiary().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({response ->
            if (response.isSuccessful) {
                diaryListLiveData.postValue(response.body())
            } else {
                diaryListLiveData.postValue(null)
            }
        }, {
            diaryListLiveData.postValue(null)
        }).apply { disposable.add(this) }
    }

    fun callGetDiaryWithTag(tags: List<String>) {
        retrofit!!.getDiary(tags).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
            if (it.isSuccessful) {
                    diaryListLiveData.postValue(it.body())
                } else {
                    diaryListLiveData.postValue(null)
                }
        }, {
                diaryListLiveData.postValue(null)
        }).apply { disposable.add(this) }
    }

    fun callGetMonthDiary(year: Int, month: Int) {
            retrofit!!.getMonthDiary(year, month).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
            if (it.isSuccessful) {
                    monthDiaryLiveData.postValue(it.body())
                } else {
                    monthDiaryLiveData.postValue(null)
                }
        }, {
                monthDiaryLiveData.postValue(null)
        }).apply { disposable.add(this) }
    }

    fun callGetDateDiary(year: Int, month: Int, day: Int) {
        retrofit!!.getDateDiary(year, month, day).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
            if (it.isSuccessful) {
                dateDiaryLiveData.postValue(it.body())
            } else {
                dateDiaryLiveData.postValue(null)
            }
        }, {
            dateDiaryLiveData.postValue(null)
        }).apply { disposable.add(this) }
    }

    fun callUpdateDiary(post_id: String, updateDiaryRequest: UpdateDiaryRequest) {
        retrofit!!.updateDiary(post_id, updateDiaryRequest).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
            if (it.isSuccessful) {
                code.postValue(it.body())
            } else {
                code.postValue(null)
            }
        }, {
            code.postValue(null)
        }).apply { disposable.add(this) }
    }

    fun callDeleteDiary(post_id: String) {
        retrofit!!.deleteDiary(post_id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
            if (it.isSuccessful) {
                code.postValue(it.body())
            } else {
                code.postValue(null)
            }
        }, {
            code.postValue(null)
        }).apply { disposable.add(this) }
    }
}