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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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

    val letter = name.firstOrNull()?.uppercase() ?: "?"

    Column(
        Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState())
    ) {
        // Avatar + email
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier.size(72.dp).clip(CircleShape).background(Color(0xFF6C63FF)),
                contentAlignment = Alignment.Center
            ) {
                Text(letter, color = Color.White, style = MaterialTheme.typography.headlineMedium)
            }
            Spacer(Modifier.width(16.dp))
            Column {
                Text(if (name.isBlank()) "Profilul meu" else name,
                    style = MaterialTheme.typography.titleLarge)
                Text(viewModel.email, style = MaterialTheme.typography.bodySmall)
            }
        }

        Spacer(Modifier.height(20.dp))
        OutlinedTextField(
            value = name, onValueChange = { viewModel.onNameChange(it) },
            label = { Text("Nume") }, singleLine = true, modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = bio, onValueChange = { viewModel.onBioChange(it) },
            label = { Text("Despre mine") }, modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        Button(onClick = { viewModel.saveProfile() }, modifier = Modifier.fillMaxWidth()) {
            Text("Salvează profilul")
        }

        Spacer(Modifier.height(24.dp))
        Text("Anunțurile mele", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))

        if (myListings.isEmpty()) {
            Text(
                "Niciun anunț cu numele tău. Setează-ți numele, apoi postează un anunț cu același nume.",
                style = MaterialTheme.typography.bodySmall
            )
        } else {
            myListings.forEach { item ->
                Card(Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Row(
                        Modifier.fillMaxWidth().padding(start = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(item.title, modifier = Modifier.weight(1f))
                        IconButton(onClick = { viewModel.deleteListing(item.id) }) {
                            Icon(Icons.Filled.Delete, contentDescription = "Șterge")
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(24.dp))
        OutlinedButton(onClick = onLogout, modifier = Modifier.fillMaxWidth()) {
            Text("Ieșire din cont")
        }
        Spacer(Modifier.height(16.dp))
    }
}