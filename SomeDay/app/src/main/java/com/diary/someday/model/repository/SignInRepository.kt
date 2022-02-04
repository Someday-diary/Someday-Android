package com.diary.someday.model.repository

import com.diary.someday.model.network.dao.UserService
import com.diary.someday.model.network.dto.response.SignInResponse
import com.diary.someday.model.network.dto.request.user.SignIn
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response

class SignInRepository(private val service: UserService) {
    fun signIn(signIn: SignIn): Observable<Response<SignInResponse>> {
        return service.signIn(signIn)
    }
}