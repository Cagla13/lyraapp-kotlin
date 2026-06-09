package com.turkcell.lyraapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.turkcell.lyraapp.ui.home.HomeScreen
import com.turkcell.lyraapp.ui.login.LoginScreen
import com.turkcell.lyraapp.ui.register.RegisterScreen
import com.turkcell.lyraapp.ui.theme.LyraAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LyraAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val context = LocalContext.current

                    NavHost(navController = navController, startDestination = "register") {

                        composable("register") {
                            RegisterScreen(
                                onNavigateToHome = {
                                    navController.navigate("home") {
                                        popUpTo("register") { inclusive = true }
                                    }
                                },
                                onNavigateToLogin = {
                                    navController.navigate("login")
                                }
                            )
                        }

                        composable("login") {
                            LoginScreen(
                                onNavigateToHome = {
                                    navController.navigate("home") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                },
                                onNavigateToRegister = {
                                    navController.navigate("register") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                }
                            )
                        }

                        composable("home") {
                            HomeScreen(
                                onNavigateToDetail = { playlistId ->
                                    Toast.makeText(context, "Müzik listesi $playlistId açılıyor...", Toast.LENGTH_SHORT).show()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}