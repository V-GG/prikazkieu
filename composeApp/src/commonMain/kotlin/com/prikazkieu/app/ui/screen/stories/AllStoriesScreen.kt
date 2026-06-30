package com.prikazkieu.app.ui.screen.stories

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import com.prikazkieu.app.data.model.Story
import com.prikazkieu.app.ui.components.AllStoriesList
import org.jetbrains.compose.resources.painterResource
import prikazkieu.composeapp.generated.resources.Res
import prikazkieu.composeapp.generated.resources.leaf_textured

@Composable
fun AllStoriesScreen(
    onStoryClick: (Story) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(Res.drawable.leaf_textured),
                contentScale = ContentScale.Crop
            )
    ) {
        AllStoriesList(
            onStoryClick = onStoryClick,
            modifier = Modifier.fillMaxSize()
        )
    }
}