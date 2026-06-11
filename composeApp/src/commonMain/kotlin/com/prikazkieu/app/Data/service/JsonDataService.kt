package com.prikazkieu.app.data.service

import com.prikazkieu.app.data.dto.JsonStoryListDto
import com.prikazkieu.app.data.model.Album
import com.prikazkieu.app.data.model.Author
import com.prikazkieu.app.data.model.Story
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.ExperimentalResourceApi
import prikazkieu.composeapp.generated.resources.Res

class JsonDataService : IDataService {
    @OptIn(ExperimentalResourceApi::class)
    override suspend fun getAllStories(): List<Story> {
        val rawJson = Res.readBytes("files/data.json").decodeToString()
        val dto = Json.decodeFromString<JsonStoryListDto>(rawJson)
        return dto.data.map { story ->
            Story(
                title = story.title,
                thumbnail = story.thumbnail,
                readTimeMinutes = story.readTimeMinutes,
                ageGroup = story.ageGroup,
                url = story.url,
                author = story.author.ifEmpty { null }
                    ?.let { Author(name = it, image = null, lived = null, origin = null) },
                kingdom = null,
                album = story.album?.let { Album(name = it.name, image = it.image) }
            )
        }
    }

    override suspend fun getStoriesByAlbum(album: String): List<Story> {
        val allStories = getAllStories()

        return allStories.filter { it.album?.name == album }
    }

    override fun getStoryContent(url: String): String {
        TODO("Not yet implemented")
    }
}
