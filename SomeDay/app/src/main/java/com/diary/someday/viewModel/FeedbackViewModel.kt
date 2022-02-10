package com.diary.someday.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.diary.someday.model.network.util.Constants.API
import com.diary.someday.model.network.dto.request.user.Feedback
import com.diary.someday.model.network.dao.UserService
import com.diary.someday.model.network.RetrofitClient
import com.diary.someday.model.repository.FeedbackRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedbackViewModel(private val repo: FeedbackRepository) : ViewModel() {
    val isSuccess: MutableLiveData<String> = MutableLiveData()

    private val disposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    fun sendFeedback(feedback: Feedback) {
        repo.sendFeedback(feedback).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.isSuccessful) {
                    isSuccess.postValue("Success")
                } else {
                    isSuccess.postValue("Failure")
                }
            }, {
                Log.d("Error", it.message.toString())
                isSuccess.postValue("Failure")
            }).apply { disposable.add(this) }
    }

}