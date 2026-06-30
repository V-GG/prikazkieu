package com.prikazkieu.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prikazkieu.app.data.model.Story
import com.prikazkieu.app.ui.viewmodel.StoriesListViewModel
import com.prikazkieu.app.ui.viewmodel.StoriesQuery

@Composable
fun StoriesListSection(
    query: StoriesQuery,
    onStoryClick: (Story) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: StoriesListViewModel = remember(query) {
        when (query) {
            is StoriesQuery.ByAlbum -> StoriesListViewModel.forAlbum(query.albumName)
            is StoriesQuery.ByKingdom -> StoriesListViewModel.forKingdom(query.kingdomName)
            is StoriesQuery.ByAuthor -> StoriesListViewModel.forAuthor(query.authorName)
            is StoriesQuery.All -> StoriesListViewModel.forAll()
        }
    }
) {
    LaunchedEffect(query) {
        viewModel.loadInitial()
    }

    val state by viewModel.state.collectAsState()
    val listState = rememberLazyListState()

    val nearEnd by remember {
        derivedStateOf {
            val lastVisible = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1
            val total = listState.layoutInfo.totalItemsCount
            total > 0 && lastVisible >= total - 3
        }
    }

    LaunchedEffect(nearEnd) {
        if (nearEnd) viewModel.loadNextPage()
    }

    when (val s = state) {
        is StoriesListViewModel.State.Loading -> {
            Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color(0xFFA52A2A))
            }
        }

        is StoriesListViewModel.State.Success -> {
            LazyColumn(
                state = listState,
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = modifier.fillMaxWidth()
            ) {
                items(s.stories, key = { it.url }) { story ->
                    StoryCard(
                        story = story,
                        onClick = onStoryClick,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                if (s.isLoadingMore) {
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = Color(0xFFA52A2A))
                        }
                    }
                }
            }
        }

        is StoriesListViewModel.State.Error -> {
            Box(modifier = modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.Center) {
                Text(text = s.message, color = Color(0xFFA52A2A), fontSize = 14.sp)
            }
        }
    }
}

@Composable
fun AlbumStoriesList(
    albumName: String,
    onStoryClick: (Story) -> Unit,
    modifier: Modifier = Modifier
) = StoriesListSection(
    query = StoriesQuery.ByAlbum(albumName),
    onStoryClick = onStoryClick,
    modifier = modifier
)

@Composable
fun KingdomStoriesList(
    kingdomName: String,
    onStoryClick: (Story) -> Unit,
    modifier: Modifier = Modifier
) = StoriesListSection(
    query = StoriesQuery.ByKingdom(kingdomName),
    onStoryClick = onStoryClick,
    modifier = modifier
)

@Composable
fun AuthorStoriesList(
    authorName: String,
    onStoryClick: (Story) -> Unit,
    modifier: Modifier = Modifier
) = StoriesListSection(
    query = StoriesQuery.ByAuthor(authorName),
    onStoryClick = onStoryClick,
    modifier = modifier
)

@Composable
fun AllStoriesList(
    onStoryClick: (Story) -> Unit,
    modifier: Modifier = Modifier
) = StoriesListSection(
    query = StoriesQuery.All,
    onStoryClick = onStoryClick,
    modifier = modifier
)