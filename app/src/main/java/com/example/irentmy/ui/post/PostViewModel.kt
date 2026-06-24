package com.example.irentmy.ui.post

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.irentmy.data.RentalItem
import com.example.irentmy.data.local.AppDatabase
import com.example.irentmy.data.remote.RetrofitClient
import com.example.irentmy.data.repository.RentalRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class PostState {
    data object Idle : PostState()
    data object Loading : PostState()
    data object Success : PostState()
    data class Error(val message: String) : PostState()
}

class PostViewModel(app: Application) : AndroidViewModel(app) {
    private val repository: RentalRepository
    private val _state = MutableStateFlow<PostState>(PostState.Idle)
    val state: StateFlow<PostState> = _state

    init {
        val db = AppDatabase.getInstance(app)
        repository = RentalRepository(RetrofitClient.api, db.rentalDao(), db.rentedDao())
    }

    fun post(item: RentalItem) {
        val problem = when {
            item.title.isBlank() -> "Completează titlul"
            item.description.isBlank() -> "Completează descrierea"
            item.ownerName.isBlank() -> "Completează numele proprietarului"
            item.pricePerHour <= 0.0 -> "Prețul pe oră trebuie să fie peste 0"
            item.pricePerDay <= 0.0 -> "Prețul pe zi trebuie să fie peste 0"
            item.pricePerMonth <= 0.0 -> "Prețul pe lună trebuie să fie peste 0"
            else -> null
        }
        if (problem != null) { _state.value = PostState.Error(problem); return }

        _state.value = PostState.Loading
        viewModelScope.launch {
            try {
                repository.createRental(item)
                _state.value = PostState.Success
            } catch (e: Exception) {
                _state.value = PostState.Error("Nu s-a putut publica anunțul")
            }
        }
    }

    fun reset() { _state.value = PostState.Idle }
}