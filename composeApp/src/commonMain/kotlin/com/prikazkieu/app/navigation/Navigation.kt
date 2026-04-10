package com.prikazkieu.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.prikazkieu.app.screen.AuthorsScreen
import com.prikazkieu.app.screen.HomeScreen
import com.prikazkieu.app.screen.KingdomsScreen
import com.prikazkieu.app.screen.LibraryScreen
import kotlinx.serialization.Serializable

@Serializable
object HomeScreen
@Serializable
object KingdomsScreen
@Serializable
object LibraryScreen
@Serializable
object AuthorsScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = HomeScreen) {
        composable<HomeScreen> { HomeScreen() }
        composable<KingdomsScreen> { KingdomsScreen() }
        composable<LibraryScreen> { LibraryScreen() }
        composable<AuthorsScreen> { AuthorsScreen() }
    }
}