package com.diary.someday.model.repository

import com.diary.someday.model.network.dao.DiaryService
import com.diary.someday.model.network.dto.request.diary.DiaryRequest
import com.diary.someday.model.network.dto.request.diary.UpdateDiaryRequest
import com.diary.someday.model.network.dto.response.PostIdResponse
import com.diary.someday.model.network.dto.response.DateDiaryResponse
import com.diary.someday.model.network.dto.response.DiaryListResponse
import com.diary.someday.model.network.dto.response.MonthDiaryResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response

class DiaryRepository(private val service: DiaryService) {
    fun callCreateDiary(diaries: DiaryRequest) : Observable<Response<PostIdResponse>> {
        return service.createDiary(diaries)
    }

    fun callGetDiaryWithTag(tags: List<String>): Observable<Response<DiaryListResponse>> {
        return service.getDiary(tags)
    }

    fun callGetAllDiary(): Observable<Response<DiaryListResponse>> {
        return service.getDiary()
    }

    fun callGetMonthDiary(year: Int, month: Int): Observable<Response<MonthDiaryResponse>> {
        return service.getMonthDiary(year, month)
    }

    fun callGetDateDiary(year: Int, month: Int, day: Int): Observable<Response<DateDiaryResponse>> {
        return service.getDateDiary(year, month, day)
    }

    fun callUpdateDiary(post_id: String, updateDiaryRequest: UpdateDiaryRequest): Observable<Response<PostIdResponse>> {
        return service.updateDiary(post_id, updateDiaryRequest)
    }

    fun callDeleteDiary(post_id: String): Observable<Response<PostIdResponse>> {
        return service.deleteDiary(post_id)
    }
}