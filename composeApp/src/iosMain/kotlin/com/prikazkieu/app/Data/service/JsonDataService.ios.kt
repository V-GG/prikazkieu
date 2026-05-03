package com.prikazkieu.app.data.service

import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.create

@OptIn(BetaInteropApi::class, ExperimentalForeignApi::class)
actual fun getJsonFromFile(file: String): String {
    val string = NSString.create(contentsOfFile = file, encoding = NSUTF8StringEncoding, error = null)

    return string.toString()
}
