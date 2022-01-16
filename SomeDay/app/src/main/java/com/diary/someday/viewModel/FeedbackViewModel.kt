package com.diary.someday.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.diary.someday.model.network.util.Constants.API
import com.diary.someday.model.network.dto.request.user.Feedback
import com.diary.someday.model.network.dao.UserService
import com.diary.someday.model.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedbackViewModel: ViewModel() {
    val isSuccess: MutableLiveData<String> = MutableLiveData()
    private val service: UserService? = RetrofitClient.getClient(API.BASE_URL)?.create(UserService::class.java)

    fun sendFeedback(feedback: Feedback) {
        service?.feedback(feedback)?.enqueue(object: Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("response code", response.code().toString() )
                    isSuccess.postValue("Success")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                isSuccess.postValue("Failure")
            }
        })
    }

}