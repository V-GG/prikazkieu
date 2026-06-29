package com.prikazkieu.app.data.model

sealed interface SearchResult {
    data class StoryResult(val story: Story) : SearchResult
    data class AuthorResult(val author: Author) : SearchResult
    data class KingdomResult(val kingdom: Kingdom) : SearchResult
}