package com.example.irentmy.ui.feed


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.irentmy.data.RentalItem

@Composable
fun RentalCard(item: RentalItem) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(item.title, style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(4.dp))
            Text(
                item.description,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2, overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(12.dp))
            Text("Preț/oră: ${item.pricePerHour} lei")
            Text("Preț/zi: ${item.pricePerDay} lei")
            Text("Preț/lună: ${item.pricePerMonth} lei")
            Spacer(Modifier.height(8.dp))
            Text(
                "Proprietar: ${item.ownerName}",
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}