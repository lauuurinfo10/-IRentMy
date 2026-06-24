package com.example.irentmy.ui.navigation


import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.irentmy.ui.checkout.CheckoutScreen
import com.example.irentmy.ui.feed.FeedScreen
import com.example.irentmy.ui.post.PostScreen
import com.example.irentmy.ui.rentals.RentalsScreen

@Composable
fun MainScreen(onLogout: () -> Unit) {
    val innerNav = rememberNavController()
    val backStack by innerNav.currentBackStackEntryAsState()
    val current = backStack?.destination?.route

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = current == "feed",
                    onClick = { innerNav.navigate("feed") { launchSingleTop = true } },
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Anunțuri") },
                    label = { Text("Anunțuri") }
                )
                NavigationBarItem(
                    selected = current == "post",
                    onClick = { innerNav.navigate("post") { launchSingleTop = true } },
                    icon = { Icon(Icons.Filled.Add, contentDescription = "Postează") },
                    label = { Text("Postează") }
                )
                NavigationBarItem(
                    selected = current == "rentals",
                    onClick = { innerNav.navigate("rentals") { launchSingleTop = true } },
                    icon = { Icon(Icons.Filled.ShoppingCart, contentDescription = "Închirieri") },
                    label = { Text("Închirieri") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onLogout,
                    icon = { Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Ieșire") },
                    label = { Text("Ieșire") }
                )
            }
        }
    ) { padding ->
        NavHost(
            navController = innerNav,
            startDestination = "feed",
            modifier = Modifier.padding(padding)
        ) {
            composable("feed") {
                FeedScreen(onRentClick = { itemId -> innerNav.navigate("checkout/$itemId") })
            }
            composable("post") {
                PostScreen(onPosted = {
                    innerNav.navigate("feed") { popUpTo("feed") { inclusive = true } }
                })
            }
            composable("rentals") { RentalsScreen() }
            composable(
                "checkout/{itemId}",
                arguments = listOf(navArgument("itemId") { type = NavType.StringType })
            ) { entry ->
                CheckoutScreen(
                    itemId = entry.arguments?.getString("itemId") ?: "",
                    onDone = { innerNav.navigate("rentals") { popUpTo("feed") } }
                )
            }
        }
    }
}