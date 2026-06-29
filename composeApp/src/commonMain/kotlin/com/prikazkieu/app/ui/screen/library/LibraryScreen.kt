package com.prikazkieu.app.ui.screen.library

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.prikazkieu.app.data.model.Story
import com.prikazkieu.app.ui.components.AllStoriesList

@Composable
fun LibraryScreen(
    onStoryClick: (Story) -> Unit,
    modifier: Modifier = Modifier
) {
    AllStoriesList(
        onStoryClick = onStoryClick,
        modifier = modifier.fillMaxSize()
    )
}