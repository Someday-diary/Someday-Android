package com.diary.someday.Retrofit


import com.google.gson.JsonElement
import com.diary.someday.Data.EmailSend
import com.diary.someday.Data.SignIn
import com.diary.someday.Data.EmailSendCheck
import com.diary.someday.Data.SignUp
import retrofit2.Call
import retrofit2.http.*

interface IRetrofit {

    @POST("user/login")
    fun signIn(@Body signIn: SignIn): Call<JsonElement>

    @POST("/user")
    fun signUp(@Body signUp: SignUp) : Call<JsonElement>

    @POST("user/verify")
    fun emailSend(@Body emailSend: EmailSend): Call<JsonElement>

    @POST("user/verify/confirm")
    fun emailSendCheck(@Body emailSendCheck: EmailSendCheck): Call<JsonElement>
}