package com.example.test.api


import com.example.test.dto.SportDto
import retrofit2.Call
import retrofit2.http.GET


interface SportService  {


    @GET("/v3/fc96c71d-d6b4-442f-b923-270f6562befd")
    fun getSportList(): Call<SportDto>
}