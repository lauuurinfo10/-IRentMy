package com.example.irentmy.ui.post

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.irentmy.data.RentalItem
import com.example.irentmy.util.PrefsManager

@Composable
fun PostScreen(
    onPosted: () -> Unit,
    viewModel: PostViewModel = viewModel()
) {
    val context = LocalContext.current
    var title by rememberSaveable { mutableStateOf("") }
    var desc by rememberSaveable { mutableStateOf("") }
    var ph by rememberSaveable { mutableStateOf("") }
    var pd by rememberSaveable { mutableStateOf("") }
    var pm by rememberSaveable { mutableStateOf("") }
    // se preia automat numele din profil → anunțul apare în "Anunțurile mele"
    var owner by rememberSaveable { mutableStateOf(PrefsManager.getName(context)) }
    var imageUrl by rememberSaveable { mutableStateOf("") }
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state) {
        if (state is PostState.Success) { onPosted(); viewModel.reset() }
    }

    val allFilled = title.isNotBlank() && desc.isNotBlank() &&
            ph.isNotBlank() && pd.isNotBlank() && pm.isNotBlank() && owner.isNotBlank()

    Column(Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState())) {
        Text("Postează un anunț", style = MaterialTheme.typography.headlineSmall)
        Text("Toate câmpurile sunt obligatorii (poza e opțională)",
            style = MaterialTheme.typography.bodySmall)
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(value = title, onValueChange = { title = it },
            label = { Text("Titlu *") }, singleLine = true, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = desc, onValueChange = { desc = it },
            label = { Text("Descriere *") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = ph, onValueChange = { ph = it.filter { c -> c.isDigit() || c == '.' } },
            label = { Text("Preț/oră *") }, singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = pd, onValueChange = { pd = it.filter { c -> c.isDigit() || c == '.' } },
            label = { Text("Preț/zi *") }, singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = pm, onValueChange = { pm = it.filter { c -> c.isDigit() || c == '.' } },
            label = { Text("Preț/lună *") }, singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = owner, onValueChange = { owner = it },
            label = { Text("Numele tău (proprietar) *") }, singleLine = true,
            modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = imageUrl, onValueChange = { imageUrl = it },
            label = { Text("Link poză (opțional)") }, singleLine = true,
            modifier = Modifier.fillMaxWidth())

        if (state is PostState.Error) {
            Spacer(Modifier.height(8.dp))
            Text((state as PostState.Error).message, color = MaterialTheme.colorScheme.error)
        }

        Spacer(Modifier.height(20.dp))
        Button(
            onClick = {
                viewModel.post(
                    RentalItem(
                        title = title, description = desc,
                        pricePerHour = ph.toDoubleOrNull() ?: 0.0,
                        pricePerDay = pd.toDoubleOrNull() ?: 0.0,
                        pricePerMonth = pm.toDoubleOrNull() ?: 0.0,
                        ownerName = owner, imageUrl = imageUrl
                    )
                )
            },
            enabled = allFilled && state !is PostState.Loading,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (state is PostState.Loading)
                CircularProgressIndicator(Modifier.size(20.dp), strokeWidth = 2.dp)
            else Text("Publică")
        }
    }
}