package com.diary.someday.Retrofit


import com.google.gson.JsonElement
import com.diary.someday.Data.EmailSend
import com.diary.someday.Data.SignIn
import com.diary.someday.Data.EmailSendCheck
import com.diary.someday.Data.SignUp
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface IRetrofit {

    @POST("user/login")
    fun signIn(@Body signIn: SignIn): Observable<Response<JsonElement>>

    @POST("user/sign_up")
    fun signUp(@Body signUp: SignUp) : Observable<Response<JsonElement>>

    @POST("user/verify")
    fun emailSend(@Body emailSend: EmailSend): Observable<Response<JsonElement>>

    @POST("user/verify/confirm")
    fun emailSendCheck(@Body emailSendCheck: EmailSendCheck): Observable<Response<JsonElement>>

    @DELETE("user/logout")
    fun logout() :Observable<Response<JsonElement>>
}