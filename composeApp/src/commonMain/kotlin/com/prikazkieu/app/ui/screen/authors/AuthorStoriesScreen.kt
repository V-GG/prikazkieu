package com.prikazkieu.app.ui.screen.authors

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.prikazkieu.app.data.model.Story
import com.prikazkieu.app.ui.components.AuthorStoriesList

@Composable
fun AuthorStoriesScreen(
    authorName: String,
    onStoryClick: (Story) -> Unit,
    modifier: Modifier = Modifier
) {
    AuthorStoriesList(
        authorName = authorName,
        onStoryClick = onStoryClick,
        modifier = modifier.fillMaxSize()
    )
}