package com.prikazkieu.app.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class JsonStoryDto(
    val author: String,
    val title: String,
    val thumbnail: String,
    val readTimeMinutes: Int,
    val ageGroup: Int,
    val url: String,
    val type: String,
    val format: String,
    val album: String? = null,
    val kingdom: String? = null,
)

@Serializable
data class JsonStoryListDto(
    val stories: List<JsonStoryDto>
)