package com.diary.someday.Retrofit


import android.util.Log
import com.diary.someday.util.TokenInterceptor
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

object RetrofitClient {
    private var retrofitClient: Retrofit? = null

    fun getClient(baseUrl: String): Retrofit? {


        val client = OkHttpClient.Builder()

        val tokenInterceptor: TokenInterceptor = TokenInterceptor()
        val logginInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                try {
                    Log.d("TAG", "JSON      " + JSONObject(message).toString(4))
                } catch (e: Exception) {
                    Log.d("TAG", "?      " + message)
                }
            }

        })

        logginInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        client.addInterceptor(tokenInterceptor)
        client.addInterceptor(logginInterceptor)







        if (retrofitClient == null) {
            retrofitClient = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.createAsync())
                .client(client.build())
                .build()
        }
        return retrofitClient
    }
}