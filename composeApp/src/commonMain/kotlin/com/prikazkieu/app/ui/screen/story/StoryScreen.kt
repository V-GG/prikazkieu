package com.prikazkieu.app.ui.screen.story

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.prikazkieu.app.ui.components.WebViewComponent

@Composable
fun StoryScreen(url: String, onBack: () -> Unit) {
    WebViewComponent(
        url = url,
        modifier = Modifier.fillMaxSize()
    )
}
