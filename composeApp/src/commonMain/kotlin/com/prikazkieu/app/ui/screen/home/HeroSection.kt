package com.prikazkieu.app.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prikazkieu.app.data.model.Story
import com.prikazkieu.app.ui.components.AgeControl
import com.prikazkieu.app.ui.components.StoryCard
import com.prikazkieu.app.ui.viewmodel.LatestStoriesViewModel
import org.jetbrains.compose.resources.painterResource
import prikazkieu.composeapp.generated.resources.Res
import prikazkieu.composeapp.generated.resources.joke_57
import prikazkieu.composeapp.generated.resources.joke_7
import prikazkieu.composeapp.generated.resources.night_sky

@Composable
fun HeroSection(
    onStoryClick: (Story) -> Unit = {},
    viewModel: LatestStoriesViewModel = remember { LatestStoriesViewModel() }
) {
    LaunchedEffect(Unit) {
        viewModel.loadIfNeeded()
    }

    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Избери си",
            style = TextStyle(
                fontSize = 28.sp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Light,
                color = Color(0xFFADB5BD),
                fontFamily = FontFamily.Serif
            )
        )

        Text(
            text = "ПРИКАЗКА",
            style = TextStyle(
                fontSize = 56.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFFFFB627),
                shadow = Shadow(
                    color = Color(0xFF5C3A00),
                    offset = Offset(4f, 6f),
                    blurRadius = 2f
                )
            )
        )

        Spacer(modifier = Modifier.height(24.dp))
        AgeControl()
        Spacer(modifier = Modifier.height(24.dp))
        Image(
            painter = painterResource(Res.drawable.joke_7),
            contentDescription = null,
            modifier = Modifier.size(128.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))

        when (val s = state) {
            is LatestStoriesViewModel.State.Loading -> {
                CircularProgressIndicator(color = Color.White)
            }

            is LatestStoriesViewModel.State.Success -> {
                LatestStoriesGrid(
                    stories = s.stories.take(4),
                    onStoryClick = onStoryClick
                )
            }

            is LatestStoriesViewModel.State.Error -> {
                Text(
                    text = s.message,
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Color.White,
                    ),
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(Res.drawable.joke_57),
            contentDescription = null,
            modifier = Modifier.size(128.dp),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
private fun LatestStoriesGrid(
    stories: List<Story>,
    onStoryClick: (Story) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        stories.chunked(2).forEach { pair ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                StoryCard(
                    story = pair[0],
                    onClick = onStoryClick,
                    modifier = Modifier.weight(1f)
                )
                if (pair.size == 2) {
                    StoryCard(
                        story = pair[1],
                        onClick = onStoryClick,
                        modifier = Modifier.weight(1f)
                    )
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}