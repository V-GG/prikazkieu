package com.prikazkieu.prikazkieu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController

@Composable
@Preview
fun App() {
    var selectedItem by remember { mutableStateOf(0) }
    // Creates the NavController
    val navController = rememberNavController()

    Scaffold(
        topBar = { TopNavBar() },
        bottomBar = { BottomNavBar(selectedItem) { selectedItem = it } }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            Navigation(navController)

            when (selectedItem) {
                BottomNavItem.Type.HOME.ordinal -> navController.navigate(HomeScreen)
                BottomNavItem.Type.KINGDOMS.ordinal -> navController.navigate(KingdomsScreen)
                BottomNavItem.Type.LIBRARY.ordinal -> navController.navigate(LibraryScreen)
                BottomNavItem.Type.AUTHORS.ordinal -> navController.navigate(AuthorsScreen)
            }
        }
    }
}