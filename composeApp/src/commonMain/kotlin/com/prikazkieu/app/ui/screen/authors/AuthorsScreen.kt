package com.prikazkieu.app.ui.screen.authors

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prikazkieu.app.ui.components.AuthorCard
import com.prikazkieu.app.ui.viewmodel.AuthorViewModel

@Composable
fun AuthorsScreen(
    onInfoClick: (String) -> Unit,
    onAuthorClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AuthorViewModel = remember { AuthorViewModel() }
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadAuthors()
    }

    when (val s = state) {
        is AuthorViewModel.State.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color(0xFFA52A2A))
            }
        }

        is AuthorViewModel.State.Success -> {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(s.authors, key = { it.name }) { author ->
                    AuthorCard(
                        author = author,
                        onInfoClick = onInfoClick,
                        onAuthorClick = onAuthorClick,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        is AuthorViewModel.State.Error -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = s.message, color = Color(0xFFA52A2A), fontSize = 14.sp)
            }
        }
    }
}