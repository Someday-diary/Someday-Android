package com.yademos.someday.Retrofit


import com.google.gson.JsonElement
import com.yademos.someday.Data.EmailSend
import com.yademos.someday.Data.SignIn
import com.yademos.someday.Data.EmailSendCheck
import com.yademos.someday.Data.SignUp
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