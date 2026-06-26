package com.prikazkieu.app.ui.components

import androidx.compose.runtime.Composable
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
