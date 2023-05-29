package com.example.test.api


import com.example.test.model.SportModel2
import retrofit2.http.GET

interface Center  {

    @GET( "center/all")
    fun getall(): List<SportModel2>
}