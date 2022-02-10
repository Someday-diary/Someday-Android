package com.diary.someday.model.repository

import com.diary.someday.model.network.dao.UserService
import com.diary.someday.model.network.dto.request.user.EmailSend
import com.diary.someday.model.network.dto.request.user.EmailSendCheck
import com.diary.someday.model.network.dto.request.user.SignUp
import com.diary.someday.model.network.dto.response.Code
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import retrofit2.Response

class SignUpRepository(private val service: UserService) {
    fun signUp(signUp: SignUp): Observable<Response<Code>> {
        return service.signUp(signUp)
    }

    fun emailSend(emailSend: EmailSend): Observable<Response<Code>> {
        return service.emailSend(emailSend)
    }

    fun emailSendCheck(emailSendCheck: EmailSendCheck): Observable<Response<Code>> {
        return service.emailSendCheck(emailSendCheck)
    }
}