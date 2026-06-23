package com.example.irentmy.ui.navigation


import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.irentmy.ui.auth.LoginScreen
import com.example.irentmy.ui.auth.RegisterScreen
import com.example.irentmy.ui.feed.FeedScreen
import com.example.irentmy.util.PrefsManager

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = "login") {

        composable("login") {
            LoginScreen(
                onLoginSuccess = { email ->
                    PrefsManager.saveEmail(context, email)   // SharedPreferences
                    navController.navigate("feed") {
                        // Back din Feed NU mai duce la Login
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToRegister = { navController.navigate("register") }
            )
        }

        composable("register") {
            RegisterScreen(
                onRegisterSuccess = { email ->
                    PrefsManager.saveEmail(context, email)
                    navController.navigate("feed") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable("feed") { FeedScreen() }
    }
}