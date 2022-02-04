package com.diary.someday.model.repository

import com.diary.someday.model.network.dao.UserService
import com.diary.someday.model.network.dto.response.Code
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response

class MainRepository(private val service: UserService) {
    fun logout(): Observable<Response<Code>> {
        return service.logout()
    }
}