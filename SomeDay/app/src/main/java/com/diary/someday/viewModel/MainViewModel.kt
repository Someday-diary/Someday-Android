package com.diary.someday.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.diary.someday.model.network.dto.response.Code
import com.diary.someday.model.repository.MainRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel(private val repo: MainRepository): ViewModel() {
    val code = MutableLiveData<Int>()
    fun logout() {
        repo.logout().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                code.postValue(it.body()?.code)
            }, {
                Log.d("Error", it.message.toString())
            })
    }
}