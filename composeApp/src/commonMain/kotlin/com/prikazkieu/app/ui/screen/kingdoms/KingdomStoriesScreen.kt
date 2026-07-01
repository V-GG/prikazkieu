package com.prikazkieu.app.ui.screen.kingdoms

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.prikazkieu.app.data.model.Story
import com.prikazkieu.app.ui.components.KingdomStoriesList

@Composable
fun KingdomStoriesScreen(
    kingdomName: String,
    onStoryClick: (Story) -> Unit,
    modifier: Modifier = Modifier
) {
    KingdomStoriesList(
        kingdomName = kingdomName,
        onStoryClick = onStoryClick,
        modifier = modifier.fillMaxSize()
    )
}