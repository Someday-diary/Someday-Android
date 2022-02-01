package com.diary.someday.model.network

import android.util.Log
import com.diary.someday.model.network.util.Constants.API.BASE_URL
import com.diary.someday.model.network.dto.request.user.EmailSend
import com.diary.someday.model.network.util.Enum.ResponseState
import com.diary.someday.model.network.dao.UserService
import com.diary.someday.model.network.dto.request.user.SignIn
import com.diary.someday.model.network.dto.request.user.EmailSendCheck
import com.diary.someday.model.network.dto.request.user.SignUp
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class RetrofitManager {
    companion object {
        val instance = RetrofitManager()
    }

    private val userService: UserService? =
        RetrofitClient.getClient(BASE_URL)?.create(UserService::class.java)

    fun signIn(email: String?, pwd: String?, completion: (ResponseState, Int, String) -> Unit) {
        val email_check: String = email.let {
            it
        } ?: ""
        val pwd_check: String = pwd.let {
            it
        } ?: ""
        val signIn = SignIn(email_check, pwd_check)
        Log.d("TAG", "email: $email_check, pwd: $pwd_check")

        userService?.signIn(signIn)?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())?.subscribe({
                if (it.code() == 200) {
                Log.d("TAG", "onResponse: ${it.code()}")
                    it.body()?.let { response ->
                        val body = response.asJsonObject
                        val code = body.get("code").asInt
                        val token = body.get("token").asString

                        Log.d("TAG", "code: $code")
                        completion(ResponseState.OKAY, code, token)
                    }
                } else {
                    it.body()?.let { isFailure ->
                        val body = isFailure.asJsonObject

                        val code = body.get("code").asInt
                        val token = body.get("token").asString

                        Log.d("TAG", "code: $code")

                        completion(ResponseState.FAIL, code, token)

                    }
                }
        },{
                Log.d("TAG", "onFailure: $it")
        })

//        val call: Call<JsonElement> = iRetrofit?.signIn(signIn).let {
//            it
//        } ?: return
//
//        call.enqueue(object : retrofit2.Callback<JsonElement> {
//            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
//                Log.d("TAG", "onResponse: ${response.code()}")
//                if (response.code() == 200) {
//                    response.body()?.let {
//                        val body = it.asJsonObject
//
//                        val code = body.get("code").asInt
//                        val token = body.get("token").asString
//
//                        Log.d("TAG", "code: $code")
//                        completion(ResponseState.OKAY, code, token)
//
//                    }
//                } else {
//                    response.body()?.let {
//                        val body = it.asJsonObject
//
//                        val code = body.get("code").asInt
//                        val token = body.get("token").asString
//
//                        Log.d("TAG", "code: $code")
//
//                        completion(ResponseState.FAIL, code, token)
//
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
//                Log.d("TAG", "onFailure: $t")
//            }
//
//        })
    }

    fun emailSend(email: String?, completion: (ResponseState, Int) -> Unit) {
        val email_check: String = email.let {
            it
        } ?: ""
        val emailSend = EmailSend(email_check)

        userService?.emailSend(emailSend)?.subscribeOn(Schedulers.io())?.subscribeOn(AndroidSchedulers.mainThread())?.subscribe({
            Log.d("TAG", "onResponse: ${it.code()}")
                if (it.code() == 200) {
                    it.body()?.let { response ->
                        val body = response.asJsonObject

                        val code = body.get("code").asInt
                        Log.d("TAG", "code: $code")
                        completion(ResponseState.OKAY, code)

                    }
                } else {
                    it.body()?.let { failure ->
                        val body = failure.asJsonObject

                        val code = body.get("code").asInt
                        Log.d("TAG", "code: $code")

                        completion(ResponseState.FAIL, code)

                    }
                }
        }, {
            Log.d("TAG", "onFailure: $it")
        })

//        val call: Call<JsonElement> = iRetrofit?.emailSend(emailSend).let {
//            it
//        } ?: return
//
//        call.enqueue(object : Callback<JsonElement> {
//            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
//                Log.d("TAG", "onResponse: ${response.code()}")
//                if (response.code() == 200) {
//                    response.body()?.let {
//                        val body = it.asJsonObject
//
//                        val code = body.get("code").asInt
//                        Log.d("TAG", "code: $code")
//                        completion(ResponseState.OKAY, code)
//
//                    }
//                } else {
//                    response.body()?.let {
//                        val body = it.asJsonObject
//
//                        val code = body.get("code").asInt
//                        Log.d("TAG", "code: $code")
//
//                        completion(ResponseState.FAIL, code)
//
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
//                Log.d("TAG", "onFailure: $t")
//            }
//
//        })
    }

    fun emailSendCheck(email: String?, code: String?, completion: (ResponseState, Int) -> Unit) {
        val email_check = email.let {
            it
        } ?: ""

        val code_check = code.let {
            it
        } ?: ""

        val emailSendCheck = EmailSendCheck(email_check, code_check)

        userService?.emailSendCheck(emailSendCheck)?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())?.subscribe({ response ->
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
        }, {
            Log.d("TAG", "onFailure: $it")
        })

//        val call: Call<JsonElement> = iRetrofit?.emailSendCheck(emailSendCheck).let {
//            it
//        } ?: return
//
//        call.enqueue(object : Callback<JsonElement> {
//            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
//                Log.d("TAG", "onResponse: ${response.code()}")
//                if (response.code() == 200) {
//                    response.body()?.let {
//                        val body = it.asJsonObject
//
//                        val code = body.get("code").asInt
//                        Log.d("TAG", "code: $code")
//                        completion(ResponseState.OKAY, code)
//
//                    }
//                } else {
//                    response.body()?.let {
//                        val body = it.asJsonObject
//
//                        val code = body.get("code").asInt
//                        Log.d("TAG", "code: $code")
//
//                        completion(ResponseState.FAIL, code)
//
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
//                Log.d("TAG", "onFailure: $t")
//            }
//
//        })


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

        userService?.signUp(signUp)?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())?.subscribe({ response ->
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
        }, {
            Log.d("TAG", "onFailure: $it")
        })

//        val call: Call<JsonElement> = iRetrofit?.signUp(signUp).let {
//            it
//        } ?: return
//
//        call.enqueue(object : Callback<JsonElement> {
//            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
//                Log.d("TAG", "onResponse: ${response.code()}")
//                if (response.code() == 200) {
//                    response.body()?.let {
//                        val body = it.asJsonObject
//
//                        val code = body.get("code").asInt
//                        Log.d("TAG", "code: $code")
//                        completion(ResponseState.OKAY, code)
//
//                    }
//                } else {
//                    response.body()?.let {
//                        val body = it.asJsonObject
//
//                        val code = body.get("code").asInt
//                        Log.d("TAG", "code: $code")
//
//                        completion(ResponseState.FAIL, code)
//
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
//                Log.d("TAG", "onFailure: $t")
//            }
//
//        })
    }

    fun logout(completion: (ResponseState, Int) -> Unit) {
        userService?.logout()?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())?.subscribe({ response ->
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
        }, {
          Log.d("TAG", "onFailure: $it")
        })

//        val call: Call<JsonElement> = iRetrofit?.logout().let {
//            it
//        } ?: return
//
//        call.enqueue(object :Callback<JsonElement>{
//            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
//                if (response.code() == 200) {
//                    response.body()?.let {
//                        val body = it.asJsonObject
//
//                        val code = body.get("code").asInt
//                        Log.d("TAG", "code: $code")
//                        completion(ResponseState.OKAY, code)
//
//                    }
//                } else {
//                    response.body()?.let {
//                        val body = it.asJsonObject
//
//                        val code = body.get("code").asInt
//                        Log.d("TAG", "code: $code")
//
//                        completion(ResponseState.FAIL, code)
//
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
//                Log.d("TAG", "onFailure: $t")
//            }
//
//        })
    }
}