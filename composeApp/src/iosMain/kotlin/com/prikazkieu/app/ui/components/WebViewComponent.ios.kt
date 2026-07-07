package com.prikazkieu.app.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import platform.Foundation.NSURL
import platform.Foundation.NSURLRequest
import platform.WebKit.WKWebView

@Composable
actual fun WebViewComponent(url: String, modifier: Modifier) {
    androidx.compose.ui.viewinterop.UIKitView(
        factory = { WKWebView() },
        update = { webView ->
            NSURL.URLWithString(url)?.let { webView.loadRequest(NSURLRequest(it)) }
        },
        modifier = modifier
    )
}

@Composable
actual fun WebViewHtmlComponent(html: String, baseUrl: String?, modifier: Modifier, disableTextSelection: Boolean) {
    var loadedHtml by remember { mutableStateOf<String?>(null) }
    androidx.compose.ui.viewinterop.UIKitView(
        factory = { WKWebView() },
        update = { webView ->
            if (loadedHtml != html) {
                val content = withSelectionDisabled(html, disableTextSelection)
                webView.loadHTMLString(content, baseUrl?.let { NSURL.URLWithString(it) })
                loadedHtml = html
            }
        },
        modifier = modifier
    )
}
