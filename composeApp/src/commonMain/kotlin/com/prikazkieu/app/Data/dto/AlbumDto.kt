package com.prikazkieu.app.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class JsonAlbumDto(
    val name: String,
    val description: String,
    val storyCount: String = "0",
    val image: String? = null,

)

@Serializable
data class JsonAlbumListDto(
    val albums: List<JsonAlbumDto>
)
