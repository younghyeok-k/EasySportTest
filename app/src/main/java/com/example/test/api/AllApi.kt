package com.example.test.api


import com.example.test.model.Data
import retrofit2.http.GET

interface AllApi {

    @GET( "user/all")
     fun getall(): List<Data>
}