package com.example.irentmy.ui.account

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AccountScreen(
    onLogout: () -> Unit,
    viewModel: AccountViewModel = viewModel()
) {
    val name by viewModel.name.collectAsState()
    val bio by viewModel.bio.collectAsState()
    val myListings by viewModel.myListings.collectAsState()

    // dacă există deja un nume salvat, pornim în modul "vizualizare" (buton Modifică)
    var editing by rememberSaveable { mutableStateOf(name.isBlank()) }
    val letter = name.firstOrNull()?.uppercase() ?: "?"

    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState())
    ) {
        // --- HEADER colorat cu avatar ---
        Column(
            Modifier.fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                Modifier.size(88.dp).clip(CircleShape).background(Color(0xFF6C63FF)),
                contentAlignment = Alignment.Center
            ) {
                Text(letter, color = Color.White, style = MaterialTheme.typography.displaySmall)
            }
            Spacer(Modifier.height(12.dp))
            Text(
                if (name.isBlank()) "Profilul meu" else name,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Text(viewModel.email, style = MaterialTheme.typography.bodyMedium)
            if (bio.isNotBlank()) {
                Spacer(Modifier.height(8.dp))
                Text(bio, style = MaterialTheme.typography.bodyMedium)
            }
        }

        Column(Modifier.padding(16.dp)) {
            // --- CARD: editare profil ---
            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text("Profil", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(12.dp))
                    OutlinedTextField(
                        value = name, onValueChange = { viewModel.onNameChange(it) },
                        label = { Text("Nume") }, singleLine = true, enabled = editing,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = bio, onValueChange = { viewModel.onBioChange(it) },
                        label = { Text("Despre mine") }, enabled = editing,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(12.dp))
                    Button(
                        onClick = {
                            if (editing) { viewModel.saveProfile(); editing = false }
                            else editing = true
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) { Text(if (editing) "Salvează" else "Modifică") }
                }
            }

            Spacer(Modifier.height(20.dp))
            Text("Anunțurile mele", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))

            if (myListings.isEmpty()) {
                Text(
                    "Niciun anunț încă. Setează-ți numele mai sus, apoi postează un anunț — va apărea aici.",
                    style = MaterialTheme.typography.bodyMedium
                )
            } else {
                myListings.forEach { item ->
                    Card(Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                        Row(
                            Modifier.fillMaxWidth().padding(start = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(Modifier.weight(1f)) {
                                Text(item.title, style = MaterialTheme.typography.titleSmall)
                                Text("${item.pricePerDay} lei/zi",
                                    style = MaterialTheme.typography.bodySmall)
                            }
                            IconButton(onClick = { viewModel.deleteListing(item.id) }) {
                                Icon(Icons.Filled.Delete, contentDescription = "Șterge",
                                    tint = MaterialTheme.colorScheme.error)
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))
            OutlinedButton(
                onClick = onLogout,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) { Text("Ieșire din cont") }
            Spacer(Modifier.height(16.dp))
        }
    }
}