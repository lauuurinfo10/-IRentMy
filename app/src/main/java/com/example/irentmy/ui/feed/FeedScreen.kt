package com.example.irentmy.ui.feed

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun FeedScreen(
    onRentClick: (String) -> Unit,
    viewModel: FeedViewModel = viewModel()
) {
    val ui by viewModel.uiState.collectAsState()

    Box(Modifier.fillMaxSize()) {
        when {
            ui.isLoading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
            ui.items.isEmpty() -> Text(
                ui.error ?: "Niciun anunț disponibil",
                Modifier.align(Alignment.Center)
            )
            else -> LazyColumn(Modifier.fillMaxSize()) {
                items(ui.items) { item ->
                    RentalCard(item = item, onRentClick = { onRentClick(item.id) })
                }
            }
        }
    }
}