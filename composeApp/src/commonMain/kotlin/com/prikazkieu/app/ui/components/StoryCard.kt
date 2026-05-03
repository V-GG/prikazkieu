package com.prikazkieu.app.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prikazkieu.app.data.model.Story

@Composable
fun StoryCard(
    story: Story,
    modifier: Modifier = Modifier,
    titleColor: Color = Color(0xFFA52A2A)
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 56.dp)
        ) {
            story.author?.let {
                Text(
                    text = it.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    color = Color.Black
                )
            }

            Spacer(Modifier.height(8.dp))

            Text(
                text = story.title,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                color = titleColor,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(16.dp))

            // TODO: replace with a network image loader (e.g. Coil AsyncImage) using story.thumbnail URL
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 8.dp)
        ) {
            ReadTimeBadge(minutes = story.readTimeMinutes)
            Spacer(Modifier.height(48.dp))
            VerticalAgeLabel(text = "${story.ageGroup}+")
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
                .size(44.dp)
                .border(width = 1.dp, color = accent, shape = CircleShape)
        ) {
            Text(
                text = "<$minutes",
                color = accent,
                fontSize = 18.sp,
                fontFamily = FontFamily.Serif
            )
        }
        Spacer(Modifier.height(4.dp))
        Text(
            text = "мин",
            color = accent,
            fontSize = 11.sp,
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
        fontSize = 16.sp,
        fontFamily = FontFamily.Serif,
        modifier = modifier.rotate(-90f)
    )
}
