package com.prikazkieu.app.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class JsonKingdomDto(
    val name: String,
    val storyCount: String = "0",
    val image: String? = null,
    val moreInfo: String? = null
)

@Serializable
data class JsonKingdomListDto(
    val kingdoms: List<JsonKingdomDto>
)
