package com.prikazkieu.app.ui.screen.story

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.prikazkieu.app.data.model.Story
import com.prikazkieu.app.ui.components.WebViewHtmlComponent
import com.prikazkieu.app.ui.navigation.LocalNavAnimatedVisibilityScope
import com.prikazkieu.app.ui.navigation.LocalSharedTransitionScope
import com.prikazkieu.app.ui.viewmodel.ReaderScreen
import com.prikazkieu.app.ui.viewmodel.ReadStoryViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ReadStoryScreen(
    story: Story,
    onBack: () -> Unit,
    onStoryClick: (Story) -> Unit = {},
    modifier: Modifier = Modifier,
    onReadingNavStateChanged: (showingSuggestions: Boolean, onDismissSuggestions: () -> Unit) -> Unit = { _, _ -> },
    viewModel: ReadStoryViewModel = viewModel(
        factory = viewModelFactory { initializer { ReadStoryViewModel.forStory(story) } }
    )
) {
    LaunchedEffect(story) {
        viewModel.loadIfNeeded()
    }

    val sharedTransitionScope = LocalSharedTransitionScope.current
    val animatedVisibilityScope = LocalNavAnimatedVisibilityScope.current

    val screenModifier = if (sharedTransitionScope != null && animatedVisibilityScope != null) {
        with(sharedTransitionScope) {
            modifier.sharedBounds(
                sharedContentState = rememberSharedContentState(key = "story-${story.url}"),
                animatedVisibilityScope = animatedVisibilityScope
            )
        }
    } else {
        modifier
    }

    val contentModifier = if (animatedVisibilityScope != null) {
        with(animatedVisibilityScope) {
            Modifier.animateEnterExit(enter = fadeIn(), exit = fadeOut())
        }
    } else {
        Modifier
    }

    Surface(color = Color.White, modifier = screenModifier.fillMaxSize()) {
        when (val current = viewModel.state.collectAsState().value) {
            is ReadStoryViewModel.State.Loading -> {
                Box(modifier = contentModifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is ReadStoryViewModel.State.Error -> {
                Box(modifier = contentModifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(current.message)
                }
            }

            is ReadStoryViewModel.State.Success -> {
                ReadStoryReader(
                    htmlContent = current.page.htmlContent,
                    viewModel = viewModel,
                    onStoryClick = onStoryClick,
                    onReadingNavStateChanged = onReadingNavStateChanged,
                    modifier = contentModifier
                )
            }
        }
    }
}

@Composable
private fun ReadStoryReader(
    htmlContent: String,
    viewModel: ReadStoryViewModel,
    onStoryClick: (Story) -> Unit,
    onReadingNavStateChanged: (showingSuggestions: Boolean, onDismissSuggestions: () -> Unit) -> Unit,
    modifier: Modifier = Modifier
) {
    val readerScreen by viewModel.readerScreen.collectAsState()
    val reachedEnd by viewModel.reachedEnd.collectAsState()

    LaunchedEffect(readerScreen) {
        onReadingNavStateChanged(readerScreen is ReaderScreen.Finished, viewModel::dismissSuggestions)
    }

    when (val screen = readerScreen) {
        is ReaderScreen.Finished -> {
            ReadStorySuggestionScreen(
                suggestions = screen.suggestions,
                onStoryClick = onStoryClick,
                modifier = modifier.fillMaxSize()
            )
        }

        else -> {
            Box(modifier = modifier.fillMaxSize()) {
                WebViewHtmlComponent(
                    html = htmlContent,
                    modifier = Modifier.fillMaxSize(),
                    disableTextSelection = true,
                    onPageInfoChanged = viewModel::onPageInfoChanged,
                    onReachedEnd = viewModel::onReachedEnd
                )

                if (screen is ReaderScreen.LoadingSuggestions) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                } else if (reachedEnd) {
                    Button(
                        onClick = { viewModel.showSuggestions() },
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(24.dp)
                    ) {
                        Text("Открий още приказки")
                    }
                }
            }
        }
    }
}