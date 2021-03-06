package com.diary.someday.model.network.dao

import com.diary.someday.model.network.dto.request.diary.DiaryRequest
import com.diary.someday.model.network.dto.request.diary.UpdateDiaryRequest
import com.diary.someday.model.network.dto.response.*
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import retrofit2.http.*

interface DiaryService {

    /* 일기 생성 */
    @POST("/diary")
    fun createDiary(@Body diaries: DiaryRequest) : Observable<Response<PostIdResponse>>

    /* 전체 일기 가져오기 */
    @GET("/diary")
    fun getDiary(@Query("tags") tags: List<String>? = null) : Observable<Response<DiaryListResponse>>

    /* 일기 가져오기 (post_id) */
    @GET("/diary/{post_id}")
    fun getDiaryWithPostId(@Path("post_id") post_id: String) : Observable<Response<DiaryResponse>>

    /*일기 가져오기 (month)*/
    @GET("/diary/month")
    fun getMonthDiary(@Query("year") year: Int, @Query("month") month: Int): Observable<Response<MonthDiaryResponse>>

    /* 일기 가져오기 (date)*/
    @GET("diary/date")
    fun getDateDiary(@Query("year") year: Int, @Query("month") month: Int, @Query("day") day: Int) : Observable<Response<DateDiaryResponse>>

    /* 일기 수정 */
    @PATCH("/diary/{post_id}")
    fun updateDiary(@Path("post_id") post_id: String, @Body updateDiaryRequest: UpdateDiaryRequest) : Observable<Response<PostIdResponse>>

    /* 일기 삭제 */
    @DELETE("/diary/{post_id}")
    fun deleteDiary(@Path("post_id") post_id: String) : Observable<Response<PostIdResponse>>
}