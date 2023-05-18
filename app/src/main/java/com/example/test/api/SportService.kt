package com.example.test.api


import com.example.test.dto.SportDto
import retrofit2.Call
import retrofit2.http.GET


interface SportService  {


    @GET("/v3/7bde037d-9563-40bf-9ecd-de3191e71ba8")
    fun getSportList(): Call<SportDto>
}