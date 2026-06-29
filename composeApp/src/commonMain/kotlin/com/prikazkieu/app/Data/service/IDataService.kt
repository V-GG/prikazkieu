package com.prikazkieu.app.data.service

import com.prikazkieu.app.data.model.Album
import com.prikazkieu.app.data.model.Author
import com.prikazkieu.app.data.model.Kingdom
import com.prikazkieu.app.data.model.Story

interface IDataService {
    suspend fun getAllStories(): List<Story>

    suspend fun getAllAlbums(): List<Album>

    suspend fun getAllKingdoms(): List<Kingdom>

    suspend fun getAllAuthors(): List<Author>

    suspend fun getStoriesByAlbum(album: String): List<Story>

    suspend fun getStoriesByKingdom(kingdom: String): List<Story>

    suspend fun getStoriesByAuthor(author: String): List<Story>

    suspend fun getStoriesPaged(page: Int, pageSize: Int): List<Story>

    suspend fun getStoriesByAlbumPaged(albumName: String, page: Int, pageSize: Int): List<Story>

    suspend fun getStoriesByKingdomPaged(kingdomName: String, page: Int, pageSize: Int): List<Story>

    fun getStoryContent(url: String): String
}
