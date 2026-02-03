package com.prikazkieu.prikazkieu

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform