package com.example.test.api

import com.example.test.dto.SportDto
import com.example.test.model.loginPost
import retrofit2.Call
import retrofit2.http.GET


interface Mlogin  {


    @GET("/v3/17114e61-6972-4092-886a-48ff45942e39")
    fun getloginList(): Call<loginPost>
}