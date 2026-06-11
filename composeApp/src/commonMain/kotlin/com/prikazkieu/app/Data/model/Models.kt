package com.prikazkieu.app.data.model

data class Story(
    val title: String,
    val thumbnail: String,
    val readTimeMinutes: Int,
    val ageGroup: Int,
    val url: String,
    val kingdom: Kingdom?,
    val author: Author?,
    val album: Album?
)

data class Kingdom(
    val name: String,
    val storyCount: Int,
)

data class Author(
    val name: String,
    val image: String?,
    val lived: String?,
    val origin: String?,
)

data class Album(
    val name: String,
    val image: String?,
)

data class Filter(val type: Filter.Type) {
    enum class Type { Magic, Common, Mythic, Animals, Songs, Riddle, Curious }
}
