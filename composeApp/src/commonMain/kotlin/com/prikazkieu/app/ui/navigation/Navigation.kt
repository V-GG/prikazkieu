package com.prikazkieu.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.prikazkieu.app.ui.screen.authors.AuthorsScreen
import com.prikazkieu.app.ui.screen.home.HomeScreen
import com.prikazkieu.app.ui.screen.kingdoms.KingdomsScreen
import com.prikazkieu.app.ui.screen.library.LibraryScreen
import com.prikazkieu.app.ui.screen.story.StoryScreen
import kotlinx.serialization.Serializable

@Serializable object HomeRoute
@Serializable object KingdomsRoute
@Serializable object LibraryRoute
@Serializable object AuthorsRoute
@Serializable data class StoryRoute(val url: String) : ITopBackNavScreen, INoBottomNavScreen

private const val BASE_URL = "https://prikazki.eu"

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = HomeRoute) {
        composable<HomeRoute> {
            HomeScreen(onStoryClick = { story ->
                navController.navigate(StoryRoute("$BASE_URL${story.url}"))
            })
        }
        composable<KingdomsRoute> { KingdomsScreen() }
        composable<LibraryRoute> { LibraryScreen() }
        composable<AuthorsRoute> { AuthorsScreen() }
        composable<StoryRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<StoryRoute>()
            StoryScreen(url = route.url, onBack = { navController.popBackStack() })
        }
    }
}
