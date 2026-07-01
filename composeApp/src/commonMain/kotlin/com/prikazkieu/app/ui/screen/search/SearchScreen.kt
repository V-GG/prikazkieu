package com.prikazkieu.app.ui.screen.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prikazkieu.app.data.model.Kingdom
import com.prikazkieu.app.data.model.SearchResult
import com.prikazkieu.app.data.model.Story
import com.prikazkieu.app.ui.components.AuthorCard
import com.prikazkieu.app.ui.components.KingdomCard
import com.prikazkieu.app.ui.components.StoryCard
import com.prikazkieu.app.ui.viewmodel.SearchViewModel

@Composable
fun SearchScreen(
    onClose: () -> Unit,
    onStoryClick: (Story) -> Unit,
    onAuthorClick: (String) -> Unit,
    onKingdomClick: (Kingdom) -> Unit,
    onInfoClick: (String) -> Unit,
    viewModel: SearchViewModel = remember { SearchViewModel() }
) {
    val state by viewModel.state.collectAsState()
    var text by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val expandedSections = remember { mutableStateMapOf<String, Boolean>() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    LaunchedEffect(state) {
        if (state is SearchViewModel.State.Results) {
            val sections = (state as SearchViewModel.State.Results).sections
            val multiSection = sections.size > 1
            expandedSections.clear()
            sections.forEach { expandedSections[it.title] = !multiSection }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Top + WindowInsetsSides.Horizontal))
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = {
                    text = it
                    viewModel.onQueryChanged(it)
                },
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
                    .focusRequester(focusRequester),
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                },
                singleLine = true
            )
            IconButton(onClick = onClose) {
                Icon(Icons.Default.Close, contentDescription = "Close search")
            }
        }

        Box(modifier = Modifier.fillMaxSize().imePadding()) {
            when (val s = state) {
                is SearchViewModel.State.Idle -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Потърси своята приказка",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Serif,
                            textAlign = TextAlign.Center,
                            color = Color(0xFFA52A2A),
                            modifier = Modifier.padding(horizontal = 32.dp)
                        )
                    }
                }

                is SearchViewModel.State.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Color(0xFFA52A2A))
                    }
                }

                is SearchViewModel.State.Results -> {
                    if (s.sections.isEmpty()) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(
                                text = "Няма намерени резултати",
                                fontSize = 16.sp,
                                color = Color.Gray
                            )
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            s.sections.forEach { section ->
                                val isExpanded = expandedSections[section.title] ?: true
                                item(key = section.title) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                expandedSections[section.title] = !isExpanded
                                            }
                                            .padding(vertical = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = "${section.title} (${section.results.size})",
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = FontFamily.Serif,
                                            color = Color(0xFFA52A2A)
                                        )
                                        Icon(
                                            imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                            contentDescription = null,
                                            tint = Color(0xFFA52A2A)
                                        )
                                    }
                                }
                                if (isExpanded) {
                                    items(section.results, key = { resultKey(it) }) { result ->
                                        when (result) {
                                            is SearchResult.StoryResult -> StoryCard(
                                                story = result.story,
                                                onClick = onStoryClick,
                                                modifier = Modifier.fillMaxWidth()
                                            )
                                            is SearchResult.AuthorResult -> AuthorCard(
                                                author = result.author,
                                                onInfoClick = onInfoClick,
                                                onAuthorClick = onAuthorClick,
                                                modifier = Modifier.fillMaxWidth()
                                            )
                                            is SearchResult.KingdomResult -> KingdomCard(
                                                kingdom = result.kingdom,
                                                onClick = onKingdomClick,
                                                onInfoClick = onInfoClick,
                                                modifier = Modifier.fillMaxWidth()
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                is SearchViewModel.State.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = s.message, color = Color(0xFFA52A2A), fontSize = 14.sp)
                    }
                }
            }
        }
    }
}

private fun resultKey(result: SearchResult): String = when (result) {
    is SearchResult.StoryResult -> "story_${result.story.url}"
    is SearchResult.AuthorResult -> "author_${result.author.name}"
    is SearchResult.KingdomResult -> "kingdom_${result.kingdom.name}"
}