package com.prikazkieu.app.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun WebViewComponent(url: String, modifier: Modifier = Modifier)

@Composable
expect fun WebViewHtmlComponent(
    html: String,
    baseUrl: String? = null,
    modifier: Modifier = Modifier,
    disableTextSelection: Boolean = false
)

private const val DISABLE_SELECTION_CSS =
    "<style>*{-webkit-user-select:none;-webkit-touch-callout:none;user-select:none;}</style>"

internal fun withSelectionDisabled(html: String, disableTextSelection: Boolean): String =
    if (disableTextSelection) DISABLE_SELECTION_CSS + html else html
