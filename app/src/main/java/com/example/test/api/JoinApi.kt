package com.example.test.api


import com.example.test.model.User
import com.example.test.model.joinPost
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface JoinApi {

    @POST( "join")
    suspend fun getJoin(
   @Body user: User
    ): Response<joinPost>
}