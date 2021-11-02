package com.diary.someday.Retrofit

import com.diary.someday.Data.Code
import com.diary.someday.Data.diary.DiaryRequest
import com.diary.someday.Data.diary.UpdateDiaryRequest
import com.diary.someday.Data.diary.Diaries
import com.diary.someday.Data.diary.Tag
import retrofit2.Call
import retrofit2.http.*

interface DiaryService {

    /* 일기 생성 */
    @POST("/diary")
    fun createDiary(@Body diaries: Diaries) : Call<Code>

    /* 전체 일기 가져오기 */
    @GET("/diary")
    fun getDiary(@Query("tags") tags: List<Tag>? = null) : Call<DiaryRequest>

    /* 일기 가져오기 (post_id) */
    @GET("/diary/{post_id}")
    fun getDiaryWithPostId(@Path("post_id") post_id: String) : Call<Diaries>

    /* 일기 수정 */
    @PATCH("/diary")
    fun updateDiary(@Body updateDiaryRequest: UpdateDiaryRequest) : Call<Code>

    /* 일기 삭제 */
    @DELETE("/diary/{post_id}")
    fun deleteDiary(@Path("post_id") post_id: String) : Call<Code>
}