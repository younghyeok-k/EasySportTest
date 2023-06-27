package com.example.test.api


import com.example.test.dto.SportDto
import retrofit2.Call
import retrofit2.http.GET


interface SportService  {

// 마커 크기좀 줄이고 맵자체 크기를 확대시키기

    //test3  https://run.mocky.io/v3/646ad845-0a0e-442b-bc94-3562fbe58da0
    //test2 https://run.mocky.io/v3/fa6758f4-84eb-40df-9104-3730f1ef5b3f

    //https://run.mocky.io/v3/14d48de7-e7c7-416d-92c7-947cc0ed19f2

    ///v3/0acf76a7-0ec0-4d5b-a537-2b9961f427a5 그전
    @GET("/v3/646ad845-0a0e-442b-bc94-3562fbe58da0")
    fun getSportList(): Call<SportDto>
}