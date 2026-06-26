package com.prikazkieu.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prikazkieu.app.data.model.Story
import com.prikazkieu.app.ui.viewmodel.AlbumStoriesViewModel

@Composable
fun AlbumStoriesSection(
    album: String,
    onStoryClick: (Story) -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: AlbumStoriesViewModel = remember { AlbumStoriesViewModel() }
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(album) {
        viewModel.loadStoriesByAlbum(album)
    }

    when (val s = state) {
        is AlbumStoriesViewModel.State.Loading -> {
            CircularProgressIndicator(
                color = Color(0xFFA52A2A),
                modifier = modifier
            )
        }
        is AlbumStoriesViewModel.State.Success -> {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = modifier.fillMaxWidth().height(320.dp)
            ) {
                items(s.stories) { story ->
                    StoryCard(
                        story = story,
                        onClick = onStoryClick,
                        modifier = Modifier.width(300.dp).fillMaxHeight()
                    )
                }
            }
        }
        is AlbumStoriesViewModel.State.Error -> {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = s.message,
                color = Color(0xFFA52A2A),
                fontSize = 14.sp
            )
        }
    }
}
