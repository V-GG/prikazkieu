package com.prikazkieu.app.ui.components

import android.annotation.SuppressLint
import android.graphics.Color as AndroidColor
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import kotlin.coroutines.resume
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.suspendCancellableCoroutine

@SuppressLint("SetJavaScriptEnabled")
@Composable
actual fun WebViewComponent(url: String, modifier: Modifier) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true
                setBackgroundColor(AndroidColor.WHITE)
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
actual fun WebViewHtmlComponent(
    html: String,
    baseUrl: String?,
    modifier: Modifier,
    disableTextSelection: Boolean,
    onPageInfoChanged: ((Int, Int) -> Unit)?,
    onReachedEnd: (() -> Unit)?
) {
    var loadedHtml by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current
    val webView = remember {
        WebView(context).apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            setBackgroundColor(AndroidColor.WHITE)
            if (disableTextSelection) {
                isLongClickable = false
                setOnLongClickListener { true }
            }
        }
    }

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

    AndroidView(
        factory = { webView },
        update = {
            if (loadedHtml != html) {
                val content = withSelectionDisabled(html, disableTextSelection)
                webView.loadDataWithBaseURL(baseUrl, content, "text/html", "UTF-8", null)
                loadedHtml = html
            }
        },
        modifier = modifier
    )
}

private suspend fun evaluateJsInt(webView: WebView, script: String): Int =
    suspendCancellableCoroutine { continuation ->
        webView.evaluateJavascript(script) { result ->
            val value = result?.toDoubleOrNull()?.toInt() ?: 0
            if (continuation.isActive) continuation.resume(value)
        }
    }

private suspend fun evaluateJsBoolean(webView: WebView, script: String): Boolean =
    suspendCancellableCoroutine { continuation ->
        webView.evaluateJavascript(script) { result ->
            val value = result?.toBooleanStrictOrNull() ?: false
            if (continuation.isActive) continuation.resume(value)
        }
    }
