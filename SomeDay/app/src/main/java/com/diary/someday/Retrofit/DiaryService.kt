package com.diary.someday.Retrofit

import com.diary.someday.Data.response.Code
import com.diary.someday.Data.request.DiaryRequest
import com.diary.someday.Data.request.Tag
import com.diary.someday.Data.request.UpdateDiaryRequest
import com.diary.someday.Data.response.DiaryResponse
import com.diary.someday.Data.response.DateDiaryResponse
import com.diary.someday.Data.response.MonthDiaryResponse
import retrofit2.Call
import retrofit2.http.*

interface DiaryService {

    /* 일기 생성 */
    @POST("/diary")
    fun createDiary(@Body diaries: DiaryRequest) : Call<Code>

    /* 전체 일기 가져오기 */
    @GET("/diary")
    fun getDiary(@Query("tags") tags: List<Tag>? = null) : Call<List<DiaryResponse>>

    /* 일기 가져오기 (post_id) */
    @GET("/diary/{post_id}")
    fun getDiaryWithPostId(@Path("post_id") post_id: String) : Call<DiaryResponse>

    /*일기 가져오기 (month)*/
    @GET("/diary/month")
    fun getMonthDiary(@Query("year") year: Int, @Query("month") month: Int): Call<MonthDiaryResponse>

    @GET("diary/Date")
    fun getDateDiary(@Query("year") year: Int, @Query("month") month: Int, @Query("day") day: Int) : Call<DateDiaryResponse>

    /* 일기 수정 */
    @PATCH("/diary")
    fun updateDiary(@Body updateDiaryRequest: UpdateDiaryRequest) : Call<Code>

    /* 일기 삭제 */
    @DELETE("/diary/{post_id}")
    fun deleteDiary(@Path("post_id") post_id: String) : Call<Code>
}