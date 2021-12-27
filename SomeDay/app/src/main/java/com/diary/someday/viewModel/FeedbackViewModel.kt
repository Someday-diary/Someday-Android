package com.diary.someday.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.diary.someday.Constants.API
import com.diary.someday.Data.Code
import com.diary.someday.Data.request.Feedback
import com.diary.someday.Retrofit.IRetrofit
import com.diary.someday.Retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedbackViewModel: ViewModel() {
    val isSuccess: MutableLiveData<String> = MutableLiveData()
    private val service: IRetrofit? = RetrofitClient.getClient(API.BASE_URL)?.create(IRetrofit::class.java)

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