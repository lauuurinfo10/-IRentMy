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
}