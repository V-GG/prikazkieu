package com.prikazkieu.app.data.service

import com.prikazkieu.app.data.model.HtmlPage
import com.prikazkieu.app.data.model.Story
import com.prikazkieu.app.data.model.StorySuggestion

interface IReadStoryService {

    val dataService: IDataService

    suspend fun getContentFor(story: Story): HtmlPage

    suspend fun getSuggestionBy(story: Story): List<StorySuggestion>
}