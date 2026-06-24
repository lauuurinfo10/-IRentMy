package com.example.irentmy.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rented_items")
data class RentedItem(
    @PrimaryKey(autoGenerate = true) val rentId: Int = 0,
    val itemTitle: String = "",
    val ownerName: String = "",
    val unit: String = "",
    val quantity: Int = 0,
    val totalPrice: Double = 0.0,
    val rentedAt: Long = 0L,
    val returnAt: Long = 0L
)