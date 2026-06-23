package com.example.irentmy.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "rental_items")
data class RentalItem(
    @PrimaryKey
    @SerializedName("id")
    val id: String = "",

    @SerializedName("title")
    val title: String = "",

    @SerializedName("description")
    val description: String = "",

    @SerializedName("pricePerHour")
    val pricePerHour: Double = 0.0,

    @SerializedName("pricePerDay")
    val pricePerDay: Double = 0.0,

    @SerializedName("pricePerMonth")
    val pricePerMonth: Double = 0.0,

    @SerializedName("ownerName")
    val ownerName: String = ""
)