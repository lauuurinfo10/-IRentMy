package com.example.irentmy.ui.account

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.irentmy.data.RentalItem
import com.example.irentmy.data.local.AppDatabase
import com.example.irentmy.data.remote.RetrofitClient
import com.example.irentmy.data.repository.RentalRepository
import com.example.irentmy.util.PrefsManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AccountViewModel(app: Application) : AndroidViewModel(app) {
    private val db = AppDatabase.getInstance(app)
    private val repository = RentalRepository(RetrofitClient.api, db.rentalDao(), db.rentedDao())
    private val ctx = app.applicationContext

    private val _name = MutableStateFlow(PrefsManager.getName(ctx))
    val name: StateFlow<String> = _name.asStateFlow()

    private val _bio = MutableStateFlow(PrefsManager.getBio(ctx))
    val bio: StateFlow<String> = _bio.asStateFlow()

    val email: String = PrefsManager.getEmail(ctx) ?: ""

    val myListings: StateFlow<List<RentalItem>> =
        combine(db.rentalDao().getAllFlow(), _name) { all, myName ->
            if (myName.isBlank()) emptyList()
            else all.filter { it.ownerName.trim().equals(myName.trim(), ignoreCase = true) }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun onNameChange(v: String) { _name.value = v }
    fun onBioChange(v: String) { _bio.value = v }

    fun saveProfile() {
        PrefsManager.saveName(ctx, _name.value.trim())
        PrefsManager.saveBio(ctx, _bio.value.trim())
    }

    fun deleteListing(id: String) {
        viewModelScope.launch { repository.deleteRental(id) }
    }
}