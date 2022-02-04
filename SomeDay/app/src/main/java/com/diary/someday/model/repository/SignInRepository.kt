package com.diary.someday.model.repository

import com.diary.someday.model.network.dao.UserService
import com.diary.someday.model.network.dto.SignInResponse
import com.diary.someday.model.network.dto.request.user.SignIn
import com.google.gson.JsonElement
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response

class SignInRepository(private val service: UserService) {
    fun signIn(signIn: SignIn): Observable<Response<SignInResponse>> {
        return service.signIn(signIn)
    }
}