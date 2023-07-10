package com.example.test.api

import com.example.test.model.Reservation.RequsetReservation
import com.example.test.model.Reservation.ResponseReservation
import retrofit2.Call
import retrofit2.http.*

interface ReservationcancelApi {
    @DELETE("center/{centerId}/reservation/{reservationId}")
    fun getReservationcancel(
        @Path("centerId") centerId: String,
        @Path("reservationId") reservationId:String
    ): Call<String>
}
