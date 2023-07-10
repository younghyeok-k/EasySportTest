package com.example.test.api

import com.example.test.model.Reservation.RequsetReservation
import com.example.test.model.Reservation.ResponseReservation
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ReservationApi {
    @POST("center/{centerId}/reservation")
    fun getReservation(
        @Path("centerId") centerId: String,
        @Body reservationInfo: RequsetReservation
    ): Call<ResponseReservation>
}
