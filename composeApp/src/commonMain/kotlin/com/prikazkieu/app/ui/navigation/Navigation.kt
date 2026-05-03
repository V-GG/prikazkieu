package com.prikazkieu.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.prikazkieu.app.ui.screen.authors.AuthorsScreen
import com.prikazkieu.app.ui.screen.home.HomeScreen
import com.prikazkieu.app.ui.screen.kingdoms.KingdomsScreen
import com.prikazkieu.app.ui.screen.library.LibraryScreen
import kotlinx.serialization.Serializable

@Serializable object HomeRoute
@Serializable object KingdomsRoute
@Serializable object LibraryRoute
@Serializable object AuthorsRoute

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = HomeRoute) {
        composable<HomeRoute> { HomeScreen() }
        composable<KingdomsRoute> { KingdomsScreen() }
        composable<LibraryRoute> { LibraryScreen() }
        composable<AuthorsRoute> { AuthorsScreen() }
    }
}