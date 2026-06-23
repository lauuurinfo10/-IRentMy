package com.example.irentmy.data.repository



import com.example.irentmy.data.RentalItem
import com.example.irentmy.data.local.RentalDao
import com.example.irentmy.data.remote.ApiService

class RentalRepository(
    private val api: ApiService,
    private val dao: RentalDao
) {

    suspend fun getRentals(): List<RentalItem> =
        try {
            val remote = api.getRentals()
            dao.insertAll(remote)
            remote
        } catch (e: Exception) {
            dao.getAll()
        }
}