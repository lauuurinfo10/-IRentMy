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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun FeedScreen(
    onRentClick: (String) -> Unit,
    viewModel: FeedViewModel = viewModel()
) {
    val ui by viewModel.uiState.collectAsState()
    val query by viewModel.query.collectAsState()


    val visible = if (query.isBlank()) ui.items
    else ui.items.filter { it.title.contains(query, ignoreCase = true) }

    Column(Modifier.fillMaxSize()) {
        OutlinedTextField(
            value = query,
            onValueChange = { viewModel.onQueryChange(it) },
            label = { Text("Caută anunț...") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth().padding(12.dp)
        )

        Box(Modifier.fillMaxSize()) {
            when {
                ui.isLoading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
                visible.isEmpty() -> Text(
                    if (ui.items.isEmpty()) (ui.error ?: "Niciun anunț disponibil")
                    else "Niciun rezultat pentru \"$query\"",
                    Modifier.align(Alignment.Center)
                )
                else -> LazyColumn(Modifier.fillMaxSize()) {
                    items(visible) { item ->
                        RentalCard(item = item, onRentClick = { onRentClick(item.id) })
                    }
                }
            }
        }
    }
}