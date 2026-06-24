package com.example.irentmy.ui.feed

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.irentmy.data.RentalItem
import com.example.irentmy.data.local.AppDatabase
import com.example.irentmy.data.remote.RetrofitClient
import com.example.irentmy.data.repository.RentalRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class FeedUiState(
    val items: List<RentalItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class FeedViewModel(app: Application) : AndroidViewModel(app) {
    private val repository: RentalRepository

    private val _uiState = MutableStateFlow(FeedUiState())
    val uiState: StateFlow<FeedUiState> = _uiState.asStateFlow()

    init {
        val db = AppDatabase.getInstance(app)
        repository = RentalRepository(RetrofitClient.api, db.rentalDao(), db.rentedDao())
        loadRentals()
    }
    fun loadRentals() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                val items = repository.getRentals()
                _uiState.value = FeedUiState(items = items, isLoading = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Nu s-au putut încărca datele"
                )
            }
        }
    }
}