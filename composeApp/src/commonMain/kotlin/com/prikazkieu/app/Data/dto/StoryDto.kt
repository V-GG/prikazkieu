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
    val album: JsonAlbumDto? = null,
)

@Serializable
data class JsonStoryListDto(
    val stories: List<JsonStoryDto>
)

@Serializable
data class JsonAlbumDto(
    val name: String,
    val image: String?
)