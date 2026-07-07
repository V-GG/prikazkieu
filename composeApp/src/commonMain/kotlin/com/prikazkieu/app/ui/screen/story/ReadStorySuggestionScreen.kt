package com.prikazkieu.app.ui.screen.story

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prikazkieu.app.data.model.Story
import com.prikazkieu.app.data.model.StorySuggestion
import com.prikazkieu.app.ui.components.StoryCard

@Composable
fun ReadStorySuggestionScreen(
    suggestions: List<StorySuggestion>,
    onStoryClick: (Story) -> Unit,
    modifier: Modifier = Modifier
) {
    val byAuthor = suggestions.filterIsInstance<StorySuggestion.AuthorSuggestion>().map { it.story }
    val byKingdom = suggestions.filterIsInstance<StorySuggestion.KingdomSuggestion>().map { it.story }

    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (byAuthor.isNotEmpty()) {
            item {
                Text(
                    text = "Още от автора",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    color = Color(0xFFA52A2A)
                )
            }
            storyRows(byAuthor, onStoryClick)
        }

        if (byKingdom.isNotEmpty()) {
            item {
                Text(
                    text = "От същото кралство",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    color = Color(0xFFA52A2A)
                )
            }
            storyRows(byKingdom, onStoryClick)
        }
    }
}

private fun LazyListScope.storyRows(
    stories: List<Story>,
    onStoryClick: (Story) -> Unit
) {
    val pairs = stories.chunked(2)
    items(pairs, key = { "row_${it.first().url}" }) { pair ->
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
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