package com.prikazkieu.app.ui.components

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@Composable
actual fun WebViewComponent(url: String, modifier: Modifier) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true
            }
        },
        update = { webView ->
            if (webView.url != url) webView.loadUrl(url)
        },
        modifier = modifier
    )
}

@SuppressLint("SetJavaScriptEnabled", "ClickableViewAccessibility")
@Composable
actual fun WebViewHtmlComponent(html: String, baseUrl: String?, modifier: Modifier, disableTextSelection: Boolean) {
    var loadedHtml by remember { mutableStateOf<String?>(null) }
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true
                if (disableTextSelection) {
                    isLongClickable = false
                    setOnLongClickListener { true }
                }
            }
        },
        update = { webView ->
            if (loadedHtml != html) {
                val content = withSelectionDisabled(html, disableTextSelection)
                webView.loadDataWithBaseURL(baseUrl, content, "text/html", "UTF-8", null)
                loadedHtml = html
            }
        },
        modifier = modifier
    )
}
