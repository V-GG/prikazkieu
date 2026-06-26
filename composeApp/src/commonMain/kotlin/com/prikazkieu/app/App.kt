package com.prikazkieu.app

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.prikazkieu.app.ui.navigation.*

@Composable
@Preview
fun App() {
    var selectedItem by remember { mutableStateOf(0) }
    val navController = rememberNavController()
    val currentDestination by navController.currentBackStackEntryAsState()
    val navBarState = NavRegistry.resolve(currentDestination?.destination)

    Scaffold(
        topBar = {
            TopNavBar(
                state = navBarState,
                onBack = { navController.popBackStack() }
            )
        },
        bottomBar = {
            if (navBarState.showBottomBar) {
                BottomNavBar(selectedItem) { newItem ->
                    selectedItem = newItem
                    when (newItem) {
                        BottomNavItem.Type.HOME.ordinal -> navController.navigate(HomeRoute)
                        BottomNavItem.Type.KINGDOMS.ordinal -> navController.navigate(KingdomsRoute)
                        BottomNavItem.Type.LIBRARY.ordinal -> navController.navigate(LibraryRoute)
                        BottomNavItem.Type.AUTHORS.ordinal -> navController.navigate(AuthorsRoute)
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
