package com.prikazkieu.app

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.prikazkieu.app.ui.navigation.*

@Composable
@Preview
fun App() {
    val navController = rememberNavController()
    val currentEntry by navController.currentBackStackEntryAsState()
    val currentDest = currentEntry?.destination
    val navBarState = NavRegistry.resolve(currentDest)

    val selectedItem = when {
        currentDest?.hasRoute<HomeRoute>() == true -> BottomNavItem.Type.HOME.ordinal
        currentDest?.hasRoute<KingdomsRoute>() == true -> BottomNavItem.Type.KINGDOMS.ordinal
        currentDest?.hasRoute<LibraryRoute>() == true -> BottomNavItem.Type.LIBRARY.ordinal
        currentDest?.hasRoute<AuthorsRoute>() == true -> BottomNavItem.Type.AUTHORS.ordinal
        else -> BottomNavItem.Type.HOME.ordinal
    }

    val tabNavOptions = navOptions {
        popUpTo<HomeRoute> { saveState = true }
        launchSingleTop = true
        restoreState = true
    }

    Scaffold(
        topBar = {
            TopNavBar(
                state = navBarState,
                onBack = { navController.popBackStack() },
                onBlogClick = { navController.navigate(AllStoriesRoute) }
            )
        },
        bottomBar = {
            if (navBarState.showBottomBar) {
                BottomNavBar(selectedItem) { newItem ->
                    when (newItem) {
                        BottomNavItem.Type.HOME.ordinal -> navController.navigate(HomeRoute, tabNavOptions)
                        BottomNavItem.Type.KINGDOMS.ordinal -> navController.navigate(KingdomsRoute, tabNavOptions)
                        BottomNavItem.Type.LIBRARY.ordinal -> navController.navigate(LibraryRoute, tabNavOptions)
                        BottomNavItem.Type.AUTHORS.ordinal -> navController.navigate(AuthorsRoute, tabNavOptions)
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = 110.dp,
                    bottom = paddingValues.calculateBottomPadding()
                )
        ) {
            Navigation(navController)
        }
    }
}
