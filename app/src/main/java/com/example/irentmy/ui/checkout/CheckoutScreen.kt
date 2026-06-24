package com.example.irentmy.ui.checkout



import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    itemId: String,
    onDone: () -> Unit,
    viewModel: CheckoutViewModel = viewModel()
) {
    LaunchedEffect(itemId) { viewModel.load(itemId) }
    val item by viewModel.item.collectAsState()
    val saved by viewModel.saved.collectAsState()

    LaunchedEffect(saved) { if (saved) onDone() }

    val it = item
    if (it == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    var unit by rememberSaveable { mutableStateOf("zi") }
    var quantityText by rememberSaveable { mutableStateOf("1") }

    val unitPrice = when (unit) {
        "oră" -> it.pricePerHour
        "zi"  -> it.pricePerDay
        else  -> it.pricePerMonth
    }
    val quantity = quantityText.toIntOrNull()?.coerceAtLeast(1) ?: 1
    val total = unitPrice * quantity

    Column(
        Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState())
    ) {
        Text(it.title, style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))


        Card(Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp)) {
                Text("Date de contact", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))
                Text("Proprietar: ${it.ownerName}")
                Text("Email: contact@irentmy.app")
                Text("Telefon: 0720 000 000")
            }
        }

        Spacer(Modifier.height(16.dp))
        Text("Alege perioada", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))
        Row {
            listOf("oră", "zi", "lună").forEach { u ->
                FilterChip(
                    selected = unit == u,
                    onClick = { unit = u },
                    label = { Text(u) }
                )
                Spacer(Modifier.width(8.dp))
            }
        }

        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = quantityText,
            onValueChange = { v -> quantityText = v.filter { c -> c.isDigit() } },
            label = { Text("Câte ($unit)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))
        Card(Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp)) {
                Text("Rezumat plată", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))
                Text("Preț/$unit: $unitPrice lei")
                Text("Cantitate: $quantity")
                Spacer(Modifier.height(4.dp))
                Text("Total: $total lei", style = MaterialTheme.typography.titleLarge)
            }
        }

        Spacer(Modifier.height(20.dp))
        Button(onClick = { viewModel.confirm(unit, quantity, unitPrice) }, modifier = Modifier.fillMaxWidth()) {
            Text("Confirmă plata (simulat)")
        }
    }
}