package com.prikazkieu.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.prikazkieu.app.data.model.Story
import com.prikazkieu.app.ui.screen.authors.AuthorStoriesScreen
import com.prikazkieu.app.ui.screen.authors.AuthorsScreen
import com.prikazkieu.app.ui.screen.home.HomeScreen
import com.prikazkieu.app.ui.screen.kingdoms.KingdomStoriesScreen
import com.prikazkieu.app.ui.screen.kingdoms.KingdomsScreen
import com.prikazkieu.app.ui.screen.library.LibraryScreen
import com.prikazkieu.app.ui.screen.stories.AlbumStoriesScreen
import com.prikazkieu.app.ui.screen.stories.AllStoriesScreen
import com.prikazkieu.app.ui.screen.story.ReadStoryScreen
import com.prikazkieu.app.ui.screen.story.StoryScreen
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

@Serializable object HomeRoute : ITopSearchNavScreen, ITopDefaultToolsScreen
@Serializable object KingdomsRoute : ITopSearchNavScreen, ITopDefaultToolsScreen
@Serializable object LibraryRoute : ITopSearchNavScreen, ITopFilterNavScreen
@Serializable object AuthorsRoute : ITopSearchNavScreen, ITopDefaultToolsScreen
@Serializable object AllStoriesRoute : ITopSearchNavScreen
@Serializable data class AlbumStoriesRoute(val albumName: String) : ITopBackNavScreen, ITopFilterNavScreen
@Serializable data class AuthorStoriesRoute(val authorName: String) : ITopBackNavScreen, ITopFilterNavScreen
@Serializable data class KingdomStoriesRoute(val kingdomName: String) : ITopBackNavScreen, ITopFilterNavScreen
@Serializable data class StoryRoute(val url: String) : ITopBackNavScreen, INoBottomNavScreen
@Serializable data class ReadStoryRoute(val story: Story) : ITopBackNavScreen, INoBottomNavScreen

internal const val BASE_URL = "https://prikazki.eu"

@Composable
fun Navigation(
    navController: NavHostController,
    showFilterSheet: Boolean = false,
    onFilterDismiss: () -> Unit = {}
) {
    NavHost(navController = navController, startDestination = HomeRoute) {
        composable<HomeRoute> {
            HomeScreen(
                onStoryClick = { story -> navController.navigate(ReadStoryRoute(story)) },
                onAlbumClick = { albumName -> navController.navigate(AlbumStoriesRoute(albumName)) }
            )
        }
        composable<AllStoriesRoute> {
            AllStoriesScreen(
                onStoryClick = { story -> navController.navigate(ReadStoryRoute(story)) },
                showFilterSheet = showFilterSheet,
                onFilterDismiss = onFilterDismiss
            )
        }
        composable<KingdomsRoute> {
            KingdomsScreen(
                onKingdomClick = { kingdom -> navController.navigate(KingdomStoriesRoute(kingdom.name)) },
                onInfoClick = { url -> navController.navigate(StoryRoute(url)) }
            )
        }
        composable<LibraryRoute> {
            LibraryScreen(
                onStoryClick = { story -> navController.navigate(ReadStoryRoute(story)) },
                showFilterSheet = showFilterSheet,
                onFilterDismiss = onFilterDismiss
            )
        }
        composable<AuthorsRoute> {
            AuthorsScreen(
                onInfoClick = { url -> navController.navigate(StoryRoute(url)) },
                onAuthorClick = { authorName -> navController.navigate(AuthorStoriesRoute(authorName)) }
            )
        }
        composable<AuthorStoriesRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<AuthorStoriesRoute>()
            AuthorStoriesScreen(
                authorName = route.authorName,
                onStoryClick = { story -> navController.navigate(ReadStoryRoute(story)) },
                showFilterSheet = showFilterSheet,
                onFilterDismiss = onFilterDismiss
            )
        }
        composable<KingdomStoriesRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<KingdomStoriesRoute>()
            KingdomStoriesScreen(
                kingdomName = route.kingdomName,
                onStoryClick = { story -> navController.navigate(ReadStoryRoute(story)) },
                showFilterSheet = showFilterSheet,
                onFilterDismiss = onFilterDismiss
            )
        }
        composable<AlbumStoriesRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<AlbumStoriesRoute>()
            AlbumStoriesScreen(
                albumName = route.albumName,
                onStoryClick = { story -> navController.navigate(ReadStoryRoute(story)) },
                showFilterSheet = showFilterSheet,
                onFilterDismiss = onFilterDismiss
            )
        }
        composable<StoryRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<StoryRoute>()
            StoryScreen(url = route.url, onBack = { navController.popBackStack() })
        }
        composable<ReadStoryRoute>(
            typeMap = mapOf(typeOf<Story>() to StoryNavType)
        ) { backStackEntry ->
            val route = backStackEntry.toRoute<ReadStoryRoute>()
            ReadStoryScreen(story = route.story, onBack = { navController.popBackStack() })
        }
    }
}
