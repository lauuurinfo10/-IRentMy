package com.example.irentmy.ui.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.irentmy.data.RentalItem

private val avatarColors = listOf(
    Color(0xFF6C63FF), Color(0xFFFF7043), Color(0xFF26A69A),
    Color(0xFFEC407A), Color(0xFF42A5F5), Color(0xFFAB47BC)
)

@Composable
fun RentalCard(item: RentalItem, onRentClick: () -> Unit) {
    val color = avatarColors[Math.floorMod(item.title.hashCode(), avatarColors.size)]
    val letter = item.title.firstOrNull()?.uppercase() ?: "?"

    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(Modifier.padding(16.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier.size(52.dp).clip(CircleShape).background(color),
                    contentAlignment = Alignment.Center
                ) {
                    Text(letter, color = Color.White,
                        style = MaterialTheme.typography.titleLarge)
                }
                Spacer(Modifier.width(12.dp))
                Column {
                    Text(item.title, style = MaterialTheme.typography.titleLarge)
                    Text("Proprietar: ${item.ownerName}",
                        style = MaterialTheme.typography.labelMedium)
                }
            }

            Spacer(Modifier.height(12.dp))
            Text(item.description, maxLines = 2, overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium)

            Spacer(Modifier.height(12.dp))
            Text("Preț/oră: ${item.pricePerHour} lei",
                color = MaterialTheme.colorScheme.primary)
            Text("Preț/zi: ${item.pricePerDay} lei",
                color = MaterialTheme.colorScheme.primary)
            Text("Preț/lună: ${item.pricePerMonth} lei",
                color = MaterialTheme.colorScheme.primary)

            Spacer(Modifier.height(12.dp))
            Row(Modifier.fillMaxWidth()) {
                Spacer(Modifier.weight(1f))
                Button(onClick = onRentClick) { Text("Inchiriaza") }
            }
        }
    }
}