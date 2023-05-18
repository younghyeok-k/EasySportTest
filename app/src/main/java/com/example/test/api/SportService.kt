package com.example.test.api


import com.example.test.dto.SportDto
import retrofit2.Call
import retrofit2.http.GET


interface SportService  {

// 마커 크기좀 줄이고 맵자체 크기를 확대시키기
    @GET("/v3/0acf76a7-0ec0-4d5b-a537-2b9961f427a5")
    fun getSportList(): Call<SportDto>
}