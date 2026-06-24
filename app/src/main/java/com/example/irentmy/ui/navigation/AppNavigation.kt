package com.example.irentmy.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.irentmy.ui.auth.LoginScreen
import com.example.irentmy.ui.auth.RegisterScreen
import com.example.irentmy.util.PrefsManager
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current


    val startScreen = if (FirebaseAuth.getInstance().currentUser != null) "main" else "login"

    NavHost(navController = navController, startDestination = startScreen) {

        composable("login") {
            LoginScreen(
                onLoginSuccess = { email ->
                    PrefsManager.saveEmail(context, email)
                    navController.navigate("main") { popUpTo("login") { inclusive = true } }
                },
                onNavigateToRegister = { navController.navigate("register") }
            )
        }

        composable("register") {
            RegisterScreen(
                onRegisterSuccess = { email ->
                    PrefsManager.saveEmail(context, email)
                    navController.navigate("main") { popUpTo("login") { inclusive = true } }
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable("main") {
            MainScreen(
                onLogout = {
                    FirebaseAuth.getInstance().signOut()
                    PrefsManager.clearSession(context)
                    navController.navigate("login") { popUpTo(0) { inclusive = true } }
                }
            )
        }
    }
}