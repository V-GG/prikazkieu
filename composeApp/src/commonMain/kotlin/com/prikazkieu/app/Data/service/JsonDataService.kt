
package com.prikazkieu.app.data.service

import com.prikazkieu.app.data.dto.JsonStoryListDto
import com.prikazkieu.app.data.model.Author
import com.prikazkieu.app.data.model.Story
import kotlinx.serialization.json.Json

class JsonDataService : IDataService {
    override fun getAllStories(): List<Story> {
        val rawJson = getJsonFromFile("/Users/viktorpetkov/Documents/prikazkieu/composeApp/src/commonMain/kotlin/com/prikazkieu/app/data/service/data.json")
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
                kingdom = null
            )
        }
    }

    override fun getStoryContent(url: String): String {
        TODO("Not yet implemented")
    }
}

expect fun getJsonFromFile(file: String): String
