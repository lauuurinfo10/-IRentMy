package com.example.irentmy.ui.rentals



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
import com.example.irentmy.data.RentedItem

private fun daysLeft(returnAt: Long): Long {
    val diff = returnAt - System.currentTimeMillis()
    return if (diff <= 0) 0 else (diff / (1000L * 60 * 60 * 24)) + 1
}

@Composable
fun RentalsScreen(viewModel: RentalsViewModel = viewModel()) {
    val items by viewModel.items.collectAsState()

    if (items.isEmpty()) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Nu ai nicio închiriere încă")
        }
        return
    }

    LazyColumn(Modifier.fillMaxSize()) {
        items(items) { rented -> RentedCard(rented) }
    }
}

@Composable
private fun RentedCard(rented: RentedItem) {
    val left = daysLeft(rented.returnAt)
    Card(Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 6.dp)) {
        Column(Modifier.padding(16.dp)) {
            Text(rented.itemTitle, style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(4.dp))
            Text("De la: ${rented.ownerName}")
            Text("Perioadă: ${rented.quantity} ${rented.unit}")
            Text("Total plătit: ${rented.totalPrice} lei")
            Spacer(Modifier.height(8.dp))
            if (left == 0L) {
                Text("⏰ Termen depășit — de returnat acum",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.titleMedium)
            } else {
                Text("Mai ai $left zile până la returnare",
                    style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}