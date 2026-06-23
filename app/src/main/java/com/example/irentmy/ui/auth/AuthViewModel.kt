package com.example.irentmy.ui.auth

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

sealed class AuthState {
    data object Idle : AuthState()
    data object Loading : AuthState()
    data object Success : AuthState()
    data class Error(val message: String) : AuthState()
}

class AuthViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()

    private val _state = MutableStateFlow<AuthState>(AuthState.Idle)
    val state: StateFlow<AuthState> = _state

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _state.value = AuthState.Error("Completează email și parolă")
            return
        }
        _state.value = AuthState.Loading
        auth.signInWithEmailAndPassword(email.trim(), password)
            .addOnCompleteListener { task ->
                _state.value = if (task.isSuccessful) AuthState.Success
                else AuthState.Error(task.exception?.localizedMessage ?: "Autentificare eșuată")
            }
    }

    fun register(email: String, password: String) {
        if (email.isBlank() || password.length < 6) {
            _state.value = AuthState.Error("Parola trebuie să aibă minim 6 caractere")
            return
        }
        _state.value = AuthState.Loading
        auth.createUserWithEmailAndPassword(email.trim(), password)
            .addOnCompleteListener { task ->
                _state.value = if (task.isSuccessful) AuthState.Success
                else AuthState.Error(task.exception?.localizedMessage ?: "Înregistrare eșuată")
            }
    }

    fun reset() { _state.value = AuthState.Idle }
}