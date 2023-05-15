package com.example.test.api


import com.example.test.model.User
import com.example.test.model.loginPost
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {


    @POST( "login")
    suspend fun getLogin(
        @Body user: User,
//   @Header("authorization") accessToken:String
        ): Call<loginPost>
}
//data class LoginRequest(
//    var username : String,
//    var password : String
//)