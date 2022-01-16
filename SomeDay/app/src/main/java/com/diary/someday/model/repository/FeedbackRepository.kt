package com.diary.someday.model.repository

import com.diary.someday.model.network.dao.UserService
import com.diary.someday.model.network.dto.request.user.Feedback
import com.google.gson.JsonElement
import io.reactivex.rxjava3.core.Observable
import org.koin.androidx.compose.inject
import retrofit2.Response

class FeedbackRepository(private val service: UserService) {
    fun sendFeedback(feedback: Feedback) : Observable<Response<Void>> {
        return service.feedback(feedback)
    }
}