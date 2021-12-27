package com.diary.someday.Retrofit


import com.diary.someday.Data.*
import com.diary.someday.Data.request.Feedback
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface IRetrofit {

    @POST("user/login")
    fun signIn(@Body signIn: SignIn): Call<JsonElement>

    @POST("user/sign_up")
    fun signUp(@Body signUp: SignUp) : Call<JsonElement>

    @POST("user/verify")
    fun emailSend(@Body emailSend: EmailSend): Call<JsonElement>

    @POST("user/verify/confirm")
    fun emailSendCheck(@Body emailSendCheck: EmailSendCheck): Call<JsonElement>

    @DELETE("user/logout")
    fun logout() :Call<JsonElement>

    @POST("feedback")
    fun feedback(@Body feedback: Feedback): Call<Void>
}