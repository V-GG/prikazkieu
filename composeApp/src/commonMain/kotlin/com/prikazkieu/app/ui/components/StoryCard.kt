package com.prikazkieu.app.ui.components

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.prikazkieu.app.data.model.Story
import com.prikazkieu.app.ui.navigation.LocalNavAnimatedVisibilityScope
import com.prikazkieu.app.ui.navigation.LocalSharedTransitionScope
import org.jetbrains.compose.resources.painterResource
import prikazkieu.composeapp.generated.resources.Res
import prikazkieu.composeapp.generated.resources.ico_audio
import prikazkieu.composeapp.generated.resources.ico_video

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun StoryCard(
    story: Story,
    onClick: (Story) -> Unit,
    modifier: Modifier = Modifier,
    titleColor: Color = Color(0xFFA52A2A)
) {
    val sharedTransitionScope = LocalSharedTransitionScope.current
    val animatedVisibilityScope = LocalNavAnimatedVisibilityScope.current

    val sharedModifier = if (sharedTransitionScope != null && animatedVisibilityScope != null) {
        with(sharedTransitionScope) {
            modifier.sharedBounds(
                sharedContentState = rememberSharedContentState(key = "story-${story.url}"),
                animatedVisibilityScope = animatedVisibilityScope
            )
        }
    } else {
        modifier
    }

    Surface(
        onClick = { onClick(story) },
        shape = RectangleShape,
        color = Color.White,
        shadowElevation = 4.dp,
        modifier = sharedModifier
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 40.dp, end = 8.dp, bottom = 8.dp)
            ) {
                story.author?.let {
                    Text(
                        text = it,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(Modifier.height(4.dp))

                Text(
                    text = story.title,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    color = titleColor,
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(Modifier.height(8.dp))

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxWidth().height(110.dp)
                ) {
                    AsyncImage(
                        model = story.thumbnail,
                        contentDescription = story.title,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.fillMaxWidth().height(110.dp)
                    )
                    when (story.format) {
                        Story.Format.audio -> Image(
                            painter = painterResource(Res.drawable.ico_audio),
                            contentDescription = "Audio",
                            modifier = Modifier.size(36.dp)
                        )
                        Story.Format.video -> Image(
                            painter = painterResource(Res.drawable.ico_video),
                            contentDescription = "Video",
                            modifier = Modifier.size(36.dp)
                        )
                        else -> {}
                    }
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 6.dp)
            ) {
                ReadTimeBadge(minutes = story.readTimeMinutes)
                Spacer(Modifier.height(32.dp))
                VerticalAgeLabel(text = "${story.ageGroup}+")
            }
        }
    }
}

@Composable
private fun ReadTimeBadge(
    minutes: Int,
    modifier: Modifier = Modifier,
    accent: Color = Color(0xFFA52A2A)
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(32.dp)
                .border(width = 1.dp, color = accent, shape = CircleShape)
        ) {
            Text(
                text = "<$minutes",
                color = accent,
                fontSize = 10.sp,
                fontFamily = FontFamily.Serif
            )
        }
        Spacer(Modifier.height(3.dp))
        Text(
            text = "мин",
            color = accent,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )
    }
}

@Composable
private fun VerticalAgeLabel(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.DarkGray
) {
    Text(
        text = text,
        color = color,
        fontSize = 11.sp,
        fontFamily = FontFamily.Serif,
        modifier = modifier.rotate(-90f)
    )
}