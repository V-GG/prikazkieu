package com.prikazkieu.app.data.service

import com.prikazkieu.app.data.model.Album
import com.prikazkieu.app.data.model.Author
import com.prikazkieu.app.data.model.Kingdom
import com.prikazkieu.app.data.model.SearchResult
import com.prikazkieu.app.data.model.Story

interface IDataService {
    suspend fun getAllStories(filterMask: Int = 0): List<Story>

    suspend fun getAllAlbums(): List<Album>

    suspend fun getAllKingdoms(): List<Kingdom>

    suspend fun getAllAuthors(): List<Author>

    suspend fun getStoriesByAlbum(album: String, filterMask: Int = 0): List<Story>

    suspend fun getStoriesByKingdom(kingdom: String, filterMask: Int = 0): List<Story>

    suspend fun getStoriesByAuthor(author: String, filterMask: Int = 0): List<Story>

    suspend fun search(query: String): List<SearchResult>

    suspend fun getStoriesPaged(page: Int, pageSize: Int, filterMask: Int = 0): List<Story>

    suspend fun getStoriesByAuthorPaged(authorName: String, page: Int, pageSize: Int, filterMask: Int = 0): List<Story>

    suspend fun getStoriesByAlbumPaged(albumName: String, page: Int, pageSize: Int, filterMask: Int = 0): List<Story>

    suspend fun getStoriesByKingdomPaged(kingdomName: String, page: Int, pageSize: Int, filterMask: Int = 0): List<Story>

    suspend fun getKingdomsPaged(page: Int, pageSize: Int): List<Kingdom>

    suspend fun getLatestStories(): List<Story>
}