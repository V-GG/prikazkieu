package com.prikazkieu.app.data.service

import com.prikazkieu.app.data.model.HtmlPage
import com.prikazkieu.app.data.model.Story
import com.prikazkieu.app.data.model.StorySuggestion

interface IReadStoryService {

    val dataService: IDataService

    suspend fun getStoryContentPaginated(url: String): List<HtmlPage>

    suspend fun getStorySuggestionBy(story: Story): List<StorySuggestion>
}