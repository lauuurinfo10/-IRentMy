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
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val _myListings = MutableStateFlow<List<RentalItem>>(emptyList())
    val myListings: StateFlow<List<RentalItem>> = _myListings.asStateFlow()

    init { loadMyListings() }

    fun onNameChange(v: String) { _name.value = v }
    fun onBioChange(v: String) { _bio.value = v }

    fun saveProfile() {
        PrefsManager.saveName(ctx, _name.value)
        PrefsManager.saveBio(ctx, _bio.value)
        loadMyListings()
    }

    fun loadMyListings() {
        viewModelScope.launch {
            val all = db.rentalDao().getAll()
            val myName = _name.value.trim()
            _myListings.value =
                if (myName.isBlank()) emptyList()
                else all.filter { it.ownerName.equals(myName, ignoreCase = true) }
        }
    }

    fun deleteListing(id: String) {
        viewModelScope.launch {
            repository.deleteRental(id)
            loadMyListings()
        }
    }
}