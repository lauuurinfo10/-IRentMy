package com.example.irentmy.ui.rentals

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.irentmy.data.RentedItem
import com.example.irentmy.data.local.AppDatabase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class RentalsViewModel(app: Application) : AndroidViewModel(app) {
    private val rentedDao = AppDatabase.getInstance(app).rentedDao()

    val items: StateFlow<List<RentedItem>> =
        rentedDao.getAll().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )
}