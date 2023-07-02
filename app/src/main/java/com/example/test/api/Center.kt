package com.example.test.api


import com.example.example.CenterResponse
import retrofit2.Call
import retrofit2.http.GET

interface Center {
    //저가 이 센터 api 도 가져오는거 확인해보고 싶은데
    @GET("center/all")
    fun getall(): Call<CenterResponse>
}