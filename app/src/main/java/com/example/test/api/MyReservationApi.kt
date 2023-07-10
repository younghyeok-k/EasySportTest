package com.example.test.api

import com.example.test.model.Reservation.MyReservations
import com.example.test.model.Reservation.RequsetReservation
import com.example.test.model.Reservation.ResponseReservation
import retrofit2.Call
import retrofit2.http.*

interface MyReservationApi {
    @GET("center/reservations")
    fun getMyReservation(
    ): Call<MyReservations>
}
