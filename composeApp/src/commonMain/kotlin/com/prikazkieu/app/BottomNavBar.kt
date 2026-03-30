package com.prikazkieu.app

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun BottomNavBar(selectedItem: Int, onItemSelected: (Int) -> Unit) {
    val items = listOf(
        BottomNavItem("Приказки", Icons.Filled.Home, BottomNavItem.Type.HOME),
        BottomNavItem("Библиотека", Icons.Filled.Person, BottomNavItem.Type.LIBRARY),
        BottomNavItem("Автори", Icons.Filled.Favorite, BottomNavItem.Type.AUTHORS),
        BottomNavItem("Царства", Icons.Filled.Search, BottomNavItem.Type.KINGDOMS),
    )

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = selectedItem == index,
                onClick = { onItemSelected(index) }
            )
        }
    }
}

data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val type: Type
) {
    enum class Type { HOME, LIBRARY, AUTHORS, KINGDOMS }
}