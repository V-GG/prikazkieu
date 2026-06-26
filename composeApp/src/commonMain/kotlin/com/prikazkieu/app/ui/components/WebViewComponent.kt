package com.prikazkieu.app.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun WebViewComponent(url: String, modifier: Modifier = Modifier)
