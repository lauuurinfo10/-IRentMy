package com.example.irentmy.ui.checkout

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.irentmy.data.RentalItem
import com.example.irentmy.data.RentedItem
import com.example.irentmy.data.local.AppDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CheckoutViewModel(app: Application) : AndroidViewModel(app) {
    private val db = AppDatabase.getInstance(app)

    private val _item = MutableStateFlow<RentalItem?>(null)
    val item: StateFlow<RentalItem?> = _item.asStateFlow()

    private val _saved = MutableStateFlow(false)
    val saved: StateFlow<Boolean> = _saved.asStateFlow()

    fun load(itemId: String) {
        viewModelScope.launch {
            _item.value = db.rentalDao().getById(itemId)
        }
    }

    fun confirm(unit: String, quantity: Int, unitPrice: Double) {
        val current = _item.value ?: return
        viewModelScope.launch {
            val now = System.currentTimeMillis()
            val durationMillis = when (unit) {
                "oră" -> quantity * 60L * 60L * 1000L
                "zi"  -> quantity * 24L * 60L * 60L * 1000L
                else  -> quantity * 30L * 24L * 60L * 60L * 1000L   
            }
            db.rentedDao().insert(
                RentedItem(
                    itemTitle = current.title,
                    ownerName = current.ownerName,
                    unit = unit,
                    quantity = quantity,
                    totalPrice = unitPrice * quantity,
                    rentedAt = now,
                    returnAt = now + durationMillis
                )
            )
            _saved.value = true
        }
    }
}