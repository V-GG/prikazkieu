package com.prikazkieu.app.data.model

data class Story(
    val title: String,
    val thumbnail: String,
    val readTimeMinutes: Int,
    val ageGroup: Int,
    val url: String,
    val kingdom: String?,
    val author: String?,
    val album: String?
)

data class Kingdom(
    val name: String,
    val image: String?,
    val moreInfo: String? = null,
    val storyCount: Int,
)

data class Author(
    val name: String,
    val image: String?,
    val lived: String?,
    val origin: String?,
    val moreInfo: String? = null,
)

data class Album(
    val name: String,
    val image: String?,
)

