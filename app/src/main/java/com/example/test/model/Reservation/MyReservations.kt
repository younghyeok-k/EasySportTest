package com.example.test.model.Reservation

import com.example.example.Content

import com.google.gson.annotations.SerializedName

data class MyReservations (
    @SerializedName("content"          ) var content          : ArrayList<RContent> = arrayListOf(),
)