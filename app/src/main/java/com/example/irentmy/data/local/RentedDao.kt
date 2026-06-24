package com.example.irentmy.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.irentmy.data.RentedItem
import kotlinx.coroutines.flow.Flow

@Dao
interface RentedDao {
    @Insert
    suspend fun insert(item: RentedItem)


    @Query("SELECT * FROM rented_items ORDER BY rentedAt DESC")
    fun getAll(): Flow<List<RentedItem>>
}