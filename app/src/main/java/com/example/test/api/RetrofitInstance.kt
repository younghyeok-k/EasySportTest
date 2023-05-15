package com.example.test.api

import android.util.Log
import com.example.test.BuildConfig

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

// 폰이랑 네트워크 연결확인 해봐야함
object RetrofitInstance {

//    val BASE_URL = "http://192.168.48.1:8080/"
    //스포츠 센터 예약 - 지도 찾고 예약까지

    val BASE_URL = "http://192.168.48.1:8080/" // ip4 내부 접속 방식
    fun createOkHttpClient(): OkHttpClient {
//        val builder = OkHttpClient.Builder()
//        val interceptor = HttpLoggingInterceptor()
//        interceptor.level = HttpLoggingInterceptor.Level.BODY
//        builder.addInterceptor(interceptor)
//        return builder.build()
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        val builder = OkHttpClient.Builder()
            .connectTimeout(200, TimeUnit.SECONDS)
            .readTimeout(200, TimeUnit.SECONDS)
            .writeTimeout(200, TimeUnit.SECONDS)
            .addInterceptor(HeaderInterceptor("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhc2QiLCJpZCI6MSwiZXhwIjoxNjgwODU0MjkxLCJ1c2VybmFtZSI6ImFzZCJ9.wZVOIiWj0xZhDR1PSGUuuDYS6iMJD-kQiqi2KvElNZ9GuOAgvV36Ca-kXUU6F-XhKMgEMmdRwlBO3fxqKm6Jiw"))
            .addNetworkInterceptor(interceptor)


        return builder.build()
    }

// interceptor


    class HeaderInterceptor constructor(private val token: String) : Interceptor {

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val token = "Bearer $token"
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", token)
                .build()
            Log.d("token", token)
            return chain.proceed(newRequest)
        }
    }


    val retrofit = Retrofit
        .Builder()
//        .client(createOkHttpClient())
        .baseUrl(BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    fun getInstance(): Retrofit {
        return retrofit
    }
}