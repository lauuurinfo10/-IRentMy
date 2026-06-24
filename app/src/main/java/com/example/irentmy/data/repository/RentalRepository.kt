package com.example.irentmy.data.repository

import com.example.irentmy.data.RentalItem
import com.example.irentmy.data.RentedItem
import com.example.irentmy.data.local.RentalDao
import com.example.irentmy.data.local.RentedDao
import com.example.irentmy.data.remote.ApiService

class RentalRepository(
    private val api: ApiService,
    private val dao: RentalDao,
    private val rentedDao: RentedDao

) {

    suspend fun getRentals(): List<RentalItem> =
        try {
            val remote = api.getRentals()
            dao.insertAll(remote)
            remote
        } catch (e: Exception) {
            dao.getAllOnce()
        }

    suspend fun createRental(item: RentalItem) {
        try {
            val created = api.createRental(item)
            dao.insertAll(listOf(created))
        } catch (e: Exception) {
            val local = item.copy(id = "local-${System.currentTimeMillis()}")
            dao.insertAll(listOf(local))
        }
    }

    suspend fun saveRented(rented: RentedItem) = rentedDao.insert(rented)

    fun getRentedItems() = rentedDao.getAll()
}