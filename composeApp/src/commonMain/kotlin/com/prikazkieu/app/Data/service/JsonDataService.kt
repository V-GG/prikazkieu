package com.prikazkieu.app.data.service

import com.prikazkieu.app.data.dto.JsonAlbumListDto
import com.prikazkieu.app.data.dto.JsonAuthorListDto
import com.prikazkieu.app.data.dto.JsonKingdomListDto
import com.prikazkieu.app.data.dto.JsonStoryListDto
import com.prikazkieu.app.data.model.Album
import com.prikazkieu.app.data.model.Author
import com.prikazkieu.app.data.model.Filter
import com.prikazkieu.app.data.model.Kingdom
import com.prikazkieu.app.data.model.SearchResult
import com.prikazkieu.app.data.model.Story
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.ExperimentalResourceApi
import prikazkieu.composeapp.generated.resources.Res

class JsonDataService : IDataService {
    private val json = Json { ignoreUnknownKeys = true }

    @OptIn(ExperimentalResourceApi::class)
    override suspend fun getAllStories(filterMask: Int): List<Story> {
        val rawJson = Res.readBytes("files/data.json").decodeToString()
        val dto = json.decodeFromString<JsonStoryListDto>(rawJson)
        val stories = dto.stories.map { story ->
            Story(
                title = story.title,
                thumbnail = story.thumbnail,
                readTimeMinutes = story.readTimeMinutes,
                ageGroup = story.ageGroup,
                url = story.url,
                type = Story.Type.entries.firstOrNull { it.displayName == story.type },
                format = Story.Format.entries.firstOrNull { it.displayName == story.format } ?: Story.Format.text,
                author = story.author.ifEmpty { null },
                kingdom = story.kingdom,
                album = story.album
            )
        }
        return applyFilterMask(stories, filterMask)
    }

    @OptIn(ExperimentalResourceApi::class)
    override suspend fun getAllAlbums(): List<Album> {
        val rawJson = Res.readBytes("files/data.json").decodeToString()
        val dto = json.decodeFromString<JsonAlbumListDto>(rawJson)
        return dto.albums.map { album ->
            Album(
                name = album.name,
                image = album.image,
                description = album.description,
                storyCount = album.storyCount.toIntOrNull() ?: 0
            )
        }
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

    override suspend fun getStoriesByAlbum(album: String, filterMask: Int): List<Story> {
        val stories = getAllStories().filter { it.album?.equals(album, ignoreCase = true) == true }
        return applyFilterMask(stories, filterMask)
    }

    override suspend fun getStoriesByKingdom(kingdom: String, filterMask: Int): List<Story> {
        val stories = getAllStories().filter { it.kingdom?.equals(kingdom, ignoreCase = true) == true }
        return applyFilterMask(stories, filterMask)
    }

    override suspend fun getStoriesByAuthor(author: String, filterMask: Int): List<Story> {
        val stories = getAllStories().filter { it.author?.equals(author, ignoreCase = true) == true }
        return applyFilterMask(stories, filterMask)
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

    override suspend fun getStoriesPaged(page: Int, pageSize: Int, filterMask: Int): List<Story> {
        val all = getAllStories(filterMask)
        val from = page * pageSize
        if (from >= all.size) return emptyList()
        return all.subList(from, minOf(from + pageSize, all.size))
    }

    override suspend fun getStoriesByAuthorPaged(authorName: String, page: Int, pageSize: Int, filterMask: Int): List<Story> {
        val all = getStoriesByAuthor(authorName, filterMask)
        val from = page * pageSize
        if (from >= all.size) return emptyList()
        return all.subList(from, minOf(from + pageSize, all.size))
    }

    override suspend fun getStoriesByAlbumPaged(albumName: String, page: Int, pageSize: Int, filterMask: Int): List<Story> {
        val all = getStoriesByAlbum(albumName, filterMask)
        val from = page * pageSize
        if (from >= all.size) return emptyList()
        return all.subList(from, minOf(from + pageSize, all.size))
    }

    override suspend fun getStoriesByKingdomPaged(kingdomName: String, page: Int, pageSize: Int, filterMask: Int): List<Story> {
        val all = getStoriesByKingdom(kingdomName, filterMask)
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

    // OR-within-group, AND-across-groups bitmask filtering
    private fun applyFilterMask(stories: List<Story>, filterMask: Int): List<Story> {
        if (filterMask == 0) return stories

        val activeReadTime = Filter.entries.filter { it.mask and filterMask != 0 && it.name.startsWith("READ_MINUTES") }
        val activeType     = Filter.entries.filter { it.mask and filterMask != 0 && it.name.startsWith("STORY_TYPE") }
        val activeFormat   = Filter.entries.filter { it.mask and filterMask != 0 && it.name.startsWith("STORY_FORMAT") }
        val activeAge      = Filter.entries.filter { it.mask and filterMask != 0 && it.name.startsWith("AGE_GROUP") }

        return stories.filter { story ->
            (activeReadTime.isEmpty() || activeReadTime.any { matchesFilter(story, it) }) &&
            (activeType.isEmpty()     || activeType.any     { matchesFilter(story, it) }) &&
            (activeFormat.isEmpty()   || activeFormat.any   { matchesFilter(story, it) }) &&
            (activeAge.isEmpty()      || activeAge.any      { matchesFilter(story, it) })
        }
    }

    private fun matchesFilter(story: Story, filter: Filter): Boolean = when (filter) {
        Filter.READ_MINUTES_2    -> story.readTimeMinutes <= 2
        Filter.READ_MINUTES_10   -> story.readTimeMinutes in 3..10
        Filter.READ_MINUTES_30   -> story.readTimeMinutes in 11..30
        Filter.READ_MINUTES_99   -> story.readTimeMinutes in 31..99
        Filter.READ_MINUTES_100  -> story.readTimeMinutes >= 100
        Filter.STORY_TYPE_FAIRY_TALES       -> story.type == Story.Type.FAIRY_TALES
        Filter.STORY_TYPE_FOLK_TALES        -> story.type == Story.Type.FOLK_TALES
        Filter.STORY_TYPE_MYTHS_AND_LEGENDS -> story.type == Story.Type.MYTHS_AND_LEGENDS
        Filter.STORY_TYPE_FABLES            -> story.type == Story.Type.FABLES
        Filter.STORY_TYPE_POEMS_AND_SONGS   -> story.type == Story.Type.POEMS_AND_SONGS
        Filter.STORY_TYPE_RIDDLES           -> story.type == Story.Type.RIDDLES
        Filter.STORY_TYPE_DID_YOU_KNOW      -> story.type == Story.Type.DID_YOU_KNOW
        Filter.STORY_FORMAT_TEXT  -> story.format == Story.Format.text
        Filter.STORY_FORMAT_AUDIO -> story.format == Story.Format.audio
        Filter.STORY_FORMAT_VIDEO -> story.format == Story.Format.video
        Filter.AGE_GROUP_3  -> story.ageGroup == 3
        Filter.AGE_GROUP_5  -> story.ageGroup == 5
        Filter.AGE_GROUP_9  -> story.ageGroup == 9
        Filter.AGE_GROUP_17 -> story.ageGroup == 17
    }
}