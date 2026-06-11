package com.prikazkieu.app.data.service

import com.prikazkieu.app.data.model.Story

interface IDataService {
    suspend fun getAllStories(): List<Story>

    suspend fun getStoriesByAlbum(album: String): List<Story>

    fun getStoryContent(url: String): String
}
