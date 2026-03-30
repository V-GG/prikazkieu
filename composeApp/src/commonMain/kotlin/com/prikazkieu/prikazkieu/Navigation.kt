package com.prikazkieu.prikazkieu

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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