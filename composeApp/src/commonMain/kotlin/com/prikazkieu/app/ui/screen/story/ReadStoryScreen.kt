package com.prikazkieu.app.ui.screen.story

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.prikazkieu.app.data.model.Story
import com.prikazkieu.app.ui.components.WebViewHtmlComponent
import com.prikazkieu.app.ui.viewmodel.ReadStoryViewModel

@Composable
fun ReadStoryScreen(
    story: Story,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ReadStoryViewModel = viewModel(
        factory = viewModelFactory { initializer { ReadStoryViewModel.forStory(story) } }
    )
) {
    LaunchedEffect(story) {
        viewModel.loadIfNeeded()
    }

    when (val current = viewModel.state.collectAsState().value) {
        is ReadStoryViewModel.State.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is ReadStoryViewModel.State.Error -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(current.message)
            }
        }

        is ReadStoryViewModel.State.Success -> {
            WebViewHtmlComponent(
                html = current.page.htmlContent,
                modifier = modifier.fillMaxSize(),
                disableTextSelection = true
            )
        }
    }
}