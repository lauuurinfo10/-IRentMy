package com.example.irentmy.ui.feed

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.irentmy.data.RentalItem
import com.example.irentmy.data.local.AppDatabase
import com.example.irentmy.data.remote.RetrofitClient
import com.example.irentmy.data.repository.RentalRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FeedViewModel(app: Application) : AndroidViewModel(app) {
    private val db = AppDatabase.getInstance(app)
    private val repository = RentalRepository(RetrofitClient.api, db.rentalDao(), db.rentedDao())


    val items: StateFlow<List<RentalItem>> =
        db.rentalDao().getAllFlow()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    init { refresh() }

    fun onQueryChange(text: String) { _query.value = text }


    fun refresh() {
        viewModelScope.launch {
            _isLoading.value = true
            try { repository.getRentals() } catch (e: Exception) { }
            _isLoading.value = false
        }
    }
}