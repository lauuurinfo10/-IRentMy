package com.example.irentmy.data.remote

import com.example.irentmy.data.RentalItem
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    // Request 1 — descarcă lista (deserializare JSON)
    @GET("rentals")
    suspend fun getRentals(): List<RentalItem>

    // Request 2 — trimite un anunț nou (serializare + deserializare JSON)
    @POST("rentals")
    suspend fun createRental(@Body item: RentalItem): RentalItem
}