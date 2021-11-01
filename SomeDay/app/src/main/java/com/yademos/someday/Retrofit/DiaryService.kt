package com.yademos.someday.Retrofit

import com.google.gson.JsonElement
import com.yademos.someday.Data.Code
import com.yademos.someday.Data.diary.DiaryRequest
import com.yademos.someday.Data.diary.TagRequest
import com.yademos.someday.Data.diary.UpdateDiaryRequest
import com.yademos.someday.Data.diary.Diaries
import retrofit2.Call
import retrofit2.http.*

interface DiaryService {

    /* 일기 생성 */
    @POST("/diary")
    fun createDiary(@Body requestDiary: DiaryRequest) : Call<Code>

    /* 일기 가져오기 */
    @GET("/diary")
    fun getDiary(@Body tags: List<TagRequest>) : Call<DiaryRequest>

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