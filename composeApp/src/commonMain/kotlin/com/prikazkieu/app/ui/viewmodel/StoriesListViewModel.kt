package com.prikazkieu.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prikazkieu.app.data.model.Story
import com.prikazkieu.app.data.service.IDataService
import com.prikazkieu.app.data.service.JsonDataService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface StoriesQuery {
    data class ByAlbum(val albumName: String) : StoriesQuery
    data class ByKingdom(val kingdomName: String) : StoriesQuery
    data class ByAuthor(val authorName: String) : StoriesQuery
    data object All : StoriesQuery
}

class StoriesListViewModel private constructor(
    private val query: StoriesQuery,
    private val dataService: IDataService
) : ViewModel() {

    companion object {
        fun forAlbum(albumName: String, dataService: IDataService = JsonDataService()) =
            StoriesListViewModel(StoriesQuery.ByAlbum(albumName), dataService)

        fun forKingdom(kingdomName: String, dataService: IDataService = JsonDataService()) =
            StoriesListViewModel(StoriesQuery.ByKingdom(kingdomName), dataService)

        fun forAuthor(authorName: String, dataService: IDataService = JsonDataService()) =
            StoriesListViewModel(StoriesQuery.ByAuthor(authorName), dataService)

        fun forAll(dataService: IDataService = JsonDataService()) =
            StoriesListViewModel(StoriesQuery.All, dataService)
    }

    sealed interface State {
        data object Loading : State
        data class Success(
            val stories: List<Story>,
            val hasMore: Boolean,
            val isLoadingMore: Boolean = false
        ) : State
        data class Error(val message: String) : State
    }

    private val pageSize = 12
    private var currentPage = 0
    private var isLoadingMore = false

    private val _state = MutableStateFlow<State>(State.Loading)
    val state: StateFlow<State> = _state.asStateFlow()

    fun loadInitial() {
        currentPage = 0
        isLoadingMore = false
        viewModelScope.launch {
            _state.value = State.Loading
            try {
                val stories = fetchPage(0)
                _state.value = State.Success(
                    stories = stories,
                    hasMore = stories.size == pageSize
                )
            } catch (e: Exception) {
                _state.value = State.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun loadNextPage() {
        val currentState = _state.value as? State.Success ?: return
        if (!currentState.hasMore || isLoadingMore) return
        isLoadingMore = true
        _state.value = currentState.copy(isLoadingMore = true)
        viewModelScope.launch {
            try {
                val nextPage = currentPage + 1
                val newStories = fetchPage(nextPage)
                currentPage = nextPage
                _state.value = State.Success(
                    stories = currentState.stories + newStories,
                    hasMore = newStories.size == pageSize
                )
            } catch (e: Exception) {
                _state.value = currentState.copy(isLoadingMore = false)
            } finally {
                isLoadingMore = false
            }
        }
    }

    private suspend fun fetchPage(page: Int): List<Story> = when (query) {
        is StoriesQuery.ByAlbum -> dataService.getStoriesByAlbumPaged(query.albumName, page, pageSize)
        is StoriesQuery.ByKingdom -> dataService.getStoriesByKingdomPaged(query.kingdomName, page, pageSize)
        is StoriesQuery.ByAuthor -> dataService.getStoriesByAuthorPaged(query.authorName, page, pageSize)
        is StoriesQuery.All -> dataService.getStoriesPaged(page, pageSize)
    }
}