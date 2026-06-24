package com.example.irentmy.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.irentmy.data.RentalItem


@Dao
interface RentalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<RentalItem>)

    @Query("SELECT * FROM rental_items")
    suspend fun getAll(): List<RentalItem>

    @Query("SELECT * FROM rental_items WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): RentalItem?


    @Query("SELECT * FROM rental_items")
    suspend fun getAllOnce(): List<RentalItem>

    @Query("DELETE FROM rental_items WHERE id = :id")
    suspend fun deleteById(id: String)
}