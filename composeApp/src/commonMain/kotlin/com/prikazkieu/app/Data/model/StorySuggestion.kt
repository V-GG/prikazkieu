package com.prikazkieu.app.data.model

interface StorySuggestion {
    data class AuthorSuggestion(val story: Story) : StorySuggestion
    data class KingdomSuggestion(val story: Story) : StorySuggestion
}