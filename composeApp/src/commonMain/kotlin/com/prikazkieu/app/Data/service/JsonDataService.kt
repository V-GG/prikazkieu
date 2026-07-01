package com.prikazkieu.app.data.service

import com.prikazkieu.app.data.dto.JsonAuthorListDto
import com.prikazkieu.app.data.dto.JsonKingdomListDto
import com.prikazkieu.app.data.dto.JsonStoryListDto
import com.prikazkieu.app.data.model.Album
import com.prikazkieu.app.data.model.Author
import com.prikazkieu.app.data.model.Kingdom
import com.prikazkieu.app.data.model.SearchResult
import com.prikazkieu.app.data.model.Story
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.ExperimentalResourceApi
import prikazkieu.composeapp.generated.resources.Res

class JsonDataService : IDataService {
    private val json = Json { ignoreUnknownKeys = true }

    @OptIn(ExperimentalResourceApi::class)
    override suspend fun getAllStories(): List<Story> {
        val rawJson = Res.readBytes("files/data.json").decodeToString()
        val dto = json.decodeFromString<JsonStoryListDto>(rawJson)
        return dto.stories.map { story ->
            Story(
                title = story.title,
                thumbnail = story.thumbnail,
                readTimeMinutes = story.readTimeMinutes,
                ageGroup = story.ageGroup,
                url = story.url,
                author = story.author.ifEmpty { null },
                kingdom = story.kingdom,
                album = story.album
            )
        }
    }

    override suspend fun getAllAlbums(): List<Album> {
        TODO("Not yet implemented")
    }

    @OptIn(ExperimentalResourceApi::class)
    override suspend fun getAllKingdoms(): List<Kingdom> {
        val rawJson = Res.readBytes("files/data.json").decodeToString()
        val dto = json.decodeFromString<JsonKingdomListDto>(rawJson)
        return dto.kingdoms.map { kingdom ->
            Kingdom(
                name = kingdom.name,
                image = kingdom.image,
                moreInfo = kingdom.moreInfo,
                storyCount = kingdom.storyCount.toIntOrNull() ?: 0
            )
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    override suspend fun getAllAuthors(): List<Author> {
        val rawJson = Res.readBytes("files/data.json").decodeToString()
        val dto = json.decodeFromString<JsonAuthorListDto>(rawJson)
        return dto.authors.map { author ->
            Author(
                name = author.name,
                image = author.image,
                lived = author.lived,
                origin = author.origin,
                moreInfo = author.moreInfo
            )
        }
    }

    override suspend fun getStoriesByAlbum(album: String): List<Story> {
        return getAllStories().filter {
            it.album?.equals(album, ignoreCase = true) == true
        }
    }

    override suspend fun getStoriesByKingdom(kingdom: String): List<Story> {
        return getAllStories().filter {
            it.kingdom?.equals(kingdom, ignoreCase = true) == true
        }
    }

    override suspend fun getStoriesByAuthor(author: String): List<Story> {
        return getAllStories().filter {
            it.author?.equals(author, ignoreCase = true) == true
        }
    }

    override suspend fun search(query: String): List<SearchResult> {
        val lower = query.lowercase()
        val results = mutableListOf<SearchResult>()

        for (story in getAllStories()) {
            if (story.title.lowercase().contains(lower)) {
                results.add(SearchResult.StoryResult(story))
            }
        }

        for (author in getAllAuthors()) {
            if (author.name.lowercase().contains(lower)) {
                results.add(SearchResult.AuthorResult(author))
            }
        }

        for (kingdom in getAllKingdoms()) {
            if (kingdom.name.lowercase().contains(lower)) {
                results.add(SearchResult.KingdomResult(kingdom))
            }
        }

        return results
    }

    override suspend fun getStoriesPaged(page: Int, pageSize: Int): List<Story> {
        val all = getAllStories()
        val from = page * pageSize
        if (from >= all.size) return emptyList()
        return all.subList(from, minOf(from + pageSize, all.size))
    }

    override suspend fun getStoriesByAuthorPaged(authorName: String, page: Int, pageSize: Int): List<Story> {
        val all = getStoriesByAuthor(authorName)
        val from = page * pageSize
        if (from >= all.size) return emptyList()
        return all.subList(from, minOf(from + pageSize, all.size))
    }

    override suspend fun getStoriesByAlbumPaged(albumName: String, page: Int, pageSize: Int): List<Story> {
        val all = getStoriesByAlbum(albumName)
        val from = page * pageSize
        if (from >= all.size) return emptyList()
        return all.subList(from, minOf(from + pageSize, all.size))
    }

    override suspend fun getStoriesByKingdomPaged(kingdomName: String, page: Int, pageSize: Int): List<Story> {
        val all = getStoriesByKingdom(kingdomName)
        val from = page * pageSize
        if (from >= all.size) return emptyList()
        return all.subList(from, minOf(from + pageSize, all.size))
    }

    override suspend fun getKingdomsPaged(page: Int, pageSize: Int): List<Kingdom> {
        val all = getAllKingdoms()
        val from = page * pageSize
        if (from >= all.size) return emptyList()
        return all.subList(from, minOf(from + pageSize, all.size))
    }

    override fun getStoryContent(url: String): String {
        TODO("Not yet implemented")
    }
}