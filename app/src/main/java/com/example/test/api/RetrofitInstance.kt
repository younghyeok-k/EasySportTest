package com.example.test.api

import android.util.Log
import com.example.test.BuildConfig

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

// 폰이랑 네트워크 연결확인 해봐야함
object RetrofitInstance {

//    val BASE_URL = "http://192.168.48.1:8080/"
    //스포츠 센터 예약 - 지도 찾고 예약까지

    val BASE_URL = "http://10.0.2.2:8080/" // ip4 내부 접속 방식




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