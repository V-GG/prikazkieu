package com.prikazkieu.app.data.service

import java.io.FileReader

actual fun getJsonFromFile(file: String): String = FileReader(file).use { it.readText() }
