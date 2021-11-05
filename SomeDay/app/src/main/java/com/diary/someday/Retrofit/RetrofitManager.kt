package com.diary.someday.Retrofit

import android.util.Log
import com.google.gson.JsonElement
import com.diary.someday.Constants.API.BASE_URL
import com.diary.someday.Data.EmailSend
import com.diary.someday.Enum.ResponseState
import com.diary.someday.Data.SignIn
import com.diary.someday.Data.EmailSendCheck
import com.diary.someday.Data.SignUp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetrofitManager {
    companion object {
        val instance = RetrofitManager()
    }

    private val iRetrofit: IRetrofit? =
        RetrofitClient.getClient(BASE_URL)?.create(IRetrofit::class.java)

    fun signIn(email: String?, pwd: String?, completion: (ResponseState, Int, String) -> Unit) {
        val email_check: String = email.let {
            it
        } ?: ""
        val pwd_check: String = pwd.let {
            it
        } ?: ""
        val signIn = SignIn(email_check, pwd_check)
        Log.d("TAG", "email: $email_check, pwd: $pwd_check")

        val call: Call<JsonElement> = iRetrofit?.signIn(signIn).let {
            it
        } ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d("TAG", "onResponse: ${response.code()}")
                if (response.code() == 200) {
                    response.body()?.let {
                        val body = it.asJsonObject

                        val code = body.get("code").asInt
                        val token = body.get("token").asString

                        Log.d("TAG", "code: $code")
                        completion(ResponseState.OKAY, code, token)

                    }
                } else {
                    response.body()?.let {
                        val body = it.asJsonObject

                        val code = body.get("code").asInt
                        val token = body.get("token").asString

                        Log.d("TAG", "code: $code")

                        completion(ResponseState.FAIL, code, token)

                    }
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d("TAG", "onFailure: $t")
            }

        })
    }

    fun emailSend(email: String?, completion: (ResponseState, Int) -> Unit) {
        val email_check: String = email.let {
            it
        } ?: ""
        val emailSend = EmailSend(email_check)

        val call: Call<JsonElement> = iRetrofit?.emailSend(emailSend).let {
            it
        } ?: return

        call.enqueue(object : Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d("TAG", "onResponse: ${response.code()}")
                if (response.code() == 200) {
                    response.body()?.let {
                        val body = it.asJsonObject

                        val code = body.get("code").asInt
                        Log.d("TAG", "code: $code")
                        completion(ResponseState.OKAY, code)

                    }
                } else {
                    response.body()?.let {
                        val body = it.asJsonObject

                        val code = body.get("code").asInt
                        Log.d("TAG", "code: $code")

                        completion(ResponseState.FAIL, code)

                    }
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d("TAG", "onFailure: $t")
            }

        })
    }

    fun emailSendCheck(email: String?, code: String?, completion: (ResponseState, Int) -> Unit) {
        val email_check = email.let {
            it
        } ?: ""

        val code_check = code.let {
            it
        } ?: ""

        val emailSendCheck = EmailSendCheck(email_check, code_check)

        val call: Call<JsonElement> = iRetrofit?.emailSendCheck(emailSendCheck).let {
            it
        } ?: return

        call.enqueue(object : Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d("TAG", "onResponse: ${response.code()}")
                if (response.code() == 200) {
                    response.body()?.let {
                        val body = it.asJsonObject

                        val code = body.get("code").asInt
                        Log.d("TAG", "code: $code")
                        completion(ResponseState.OKAY, code)

                    }
                } else {
                    response.body()?.let {
                        val body = it.asJsonObject

                        val code = body.get("code").asInt
                        Log.d("TAG", "code: $code")

                        completion(ResponseState.FAIL, code)

                    }
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d("TAG", "onFailure: $t")
            }

        })


    }

    fun signUp(
        email: String?,
        pwd: String?,
        agree: String,
        completion: (ResponseState, Int) -> Unit
    ) {
        val email_check = email.let {
            it
        } ?: ""
        val pwd_check = pwd.let {
            it
        } ?: ""

        val signUp = SignUp(email_check, pwd_check, agree)

        val call: Call<JsonElement> = iRetrofit?.signUp(signUp).let {
            it
        } ?: return

        call.enqueue(object : Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d("TAG", "onResponse: ${response.code()}")
                if (response.code() == 200) {
                    response.body()?.let {
                        val body = it.asJsonObject

                        val code = body.get("code").asInt
                        Log.d("TAG", "code: $code")
                        completion(ResponseState.OKAY, code)

                    }
                } else {
                    response.body()?.let {
                        val body = it.asJsonObject

                        val code = body.get("code").asInt
                        Log.d("TAG", "code: $code")

                        completion(ResponseState.FAIL, code)

                    }
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d("TAG", "onFailure: $t")
            }

        })
    }


}