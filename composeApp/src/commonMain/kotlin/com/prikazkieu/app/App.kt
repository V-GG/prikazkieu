package com.prikazkieu.app

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.prikazkieu.app.navigation.*

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = 110.dp, // TODO: this needs to be resolved
                    bottom = paddingValues.calculateBottomPadding()
                )
        ) {
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