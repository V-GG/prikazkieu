package com.prikazkieu.app.data.service

import com.prikazkieu.app.data.model.Story

interface IDataService {
    fun getAllStories(): List<Story>
    fun getStoryContent(url: String): String
}
