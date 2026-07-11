package com.prikazkieu.app.ui.screen.kingdoms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
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
import com.prikazkieu.app.data.model.Kingdom
import com.prikazkieu.app.ui.components.KingdomCard
import com.prikazkieu.app.ui.viewmodel.KingdomViewModel

@Composable
fun KingdomsScreen(
    onKingdomClick: (Kingdom) -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: KingdomViewModel = remember { KingdomViewModel() }
) {
    val state by viewModel.state.collectAsState()
    val listState = rememberLazyGridState()

    LaunchedEffect(Unit) {
        viewModel.loadInitial()
    }

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
        is KingdomViewModel.State.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color(0xFFA52A2A))
            }
        }

        is KingdomViewModel.State.Success -> {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                state = listState,
                modifier = modifier.fillMaxSize(),
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(s.kingdoms, key = { it.name }) { kingdom ->
                    KingdomCard(
                        kingdom = kingdom,
                        onClick = onKingdomClick
                    )
                }
                if (s.isLoadingMore) {
                    item(span = { GridItemSpan(2) }) {
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

        is KingdomViewModel.State.Error -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = s.message, color = Color(0xFFA52A2A), fontSize = 14.sp)
            }
        }
    }
}