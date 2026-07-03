package com.prikazkieu.app.ui.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Article
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TopNavBar(
    state: NavBarState = NavBarState(),
    onBack: () -> Unit = {},
    onBlogClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    onFilterClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            if (state.showBack) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            }

            if (state.showSearch) {
                Box(modifier = Modifier.weight(1f)) {
                    OutlinedTextField(
                        state = TextFieldState(),
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        leadingIcon = {
                            Icon(Icons.Default.Search, contentDescription = "Search")
                        }
                    )
                    Box(modifier = Modifier.matchParentSize().clickable { onSearchClick() })
                }
            }

            if (state.showDefault) {
                IconButton(onClick = onBlogClick) {
                    Icon(Icons.AutoMirrored.Filled.Article, contentDescription = "Blog")
                }
                IconButton(onClick = { }) {
                    Icon(Icons.Filled.Favorite, contentDescription = "Blog", tint = Color.Red)
                }
            }

            if (state.showFilter) {
                IconButton(onClick = onFilterClick) {
                    Icon(Icons.Filled.FilterList, contentDescription = "Filter")
                }
            }
        }
    }
}
