package com.prikazkieu.app.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlin.coroutines.resume
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.Foundation.NSNumber
import platform.Foundation.NSURL
import platform.Foundation.NSURLRequest
import platform.UIKit.UIColor
import platform.WebKit.WKWebView

@Composable
actual fun WebViewComponent(url: String, modifier: Modifier) {
    androidx.compose.ui.viewinterop.UIKitView(
        factory = { WKWebView().apply { backgroundColor = UIColor.whiteColor; opaque = true } },
        update = { webView ->
            NSURL.URLWithString(url)?.let { webView.loadRequest(NSURLRequest(it)) }
        },
        modifier = modifier
    )
}

@Composable
actual fun WebViewHtmlComponent(
    html: String,
    baseUrl: String?,
    modifier: Modifier,
    disableTextSelection: Boolean,
    onPageInfoChanged: ((Int, Int) -> Unit)?,
    onReachedEnd: (() -> Unit)?
) {
    var loadedHtml by remember { mutableStateOf<String?>(null) }
    val webView = remember { WKWebView().apply { backgroundColor = UIColor.whiteColor; opaque = true } }

    if (onPageInfoChanged != null || onReachedEnd != null) {
        LaunchedEffect(webView) {
            var wasAtEnd = false
            while (isActive) {
                delay(400)
                val page = evaluateJsInt(webView, "window.getCurrentPage ? window.getCurrentPage() : 0")
                val total = evaluateJsInt(webView, "window.getPageCount ? window.getPageCount() : 1")
                onPageInfoChanged?.invoke(page, total)

                if (onReachedEnd != null) {
                    val atEnd = evaluateJsBoolean(webView, "window.isLastPage ? window.isLastPage() : false")
                    if (atEnd && !wasAtEnd) onReachedEnd()
                    wasAtEnd = atEnd
                }
            }
        }
    }

    androidx.compose.ui.viewinterop.UIKitView(
        factory = { webView },
        update = {
            if (loadedHtml != html) {
                val content = withSelectionDisabled(html, disableTextSelection)
                webView.loadHTMLString(content, baseUrl?.let { NSURL.URLWithString(it) })
                loadedHtml = html
            }
        },
        modifier = modifier
    )
}

private suspend fun evaluateJsInt(webView: WKWebView, script: String): Int =
    suspendCancellableCoroutine { continuation ->
        webView.evaluateJavaScript(script) { result, _ ->
            val value = (result as? NSNumber)?.intValue ?: 0
            if (continuation.isActive) continuation.resume(value)
        }
    }

private suspend fun evaluateJsBoolean(webView: WKWebView, script: String): Boolean =
    suspendCancellableCoroutine { continuation ->
        webView.evaluateJavaScript(script) { result, _ ->
            val value = (result as? NSNumber)?.boolValue ?: false
            if (continuation.isActive) continuation.resume(value)
        }
    }
