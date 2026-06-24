package com.example.irentmy.data.remote

import com.example.irentmy.data.RentalItem
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.DELETE
import retrofit2.http.Path


interface ApiService {
    @GET("rentals")
    suspend fun getRentals(): List<RentalItem>

    @POST("rentals")
    suspend fun createRental(@Body item: RentalItem): RentalItem

    @DELETE("rentals/{id}")
    suspend fun deleteRental(@Path("id") id: String)
}