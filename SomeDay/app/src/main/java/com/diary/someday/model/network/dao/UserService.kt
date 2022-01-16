package com.diary.someday.model.network.dao


import com.diary.someday.model.network.dto.request.user.Feedback
import com.google.gson.JsonElement
import com.diary.someday.model.network.dto.request.user.EmailSend
import com.diary.someday.model.network.dto.request.user.SignIn
import com.diary.someday.model.network.dto.request.user.EmailSendCheck
import com.diary.someday.model.network.dto.request.user.SignUp
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface UserService {

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

    @POST("feedback")
    fun feedback(@Body feedback: Feedback): Call<Void>
}