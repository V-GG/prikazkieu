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
)

@Serializable
data class JsonStoryListDto(
    val data: List<JsonStoryDto>
)
