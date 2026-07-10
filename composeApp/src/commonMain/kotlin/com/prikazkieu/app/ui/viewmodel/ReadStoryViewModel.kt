package com.prikazkieu.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prikazkieu.app.data.model.HtmlPage
import com.prikazkieu.app.data.model.Story
import com.prikazkieu.app.data.model.StorySuggestion
import com.prikazkieu.app.data.service.IDataService
import com.prikazkieu.app.data.service.JsonDataService
import com.prikazkieu.app.data.service.ReadStoryService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// commonMain — presentation
sealed interface ReaderScreen {
    data class Reading(val page: Int, val total: Int) : ReaderScreen
    data object LoadingSuggestions : ReaderScreen
    data class Finished(val suggestions: List<StorySuggestion>) : ReaderScreen
}

class ReadStoryViewModel private constructor(
    private val story: Story,
    private val dataService: IDataService
) : ViewModel() {

    companion object {
        fun forStory(story: Story, dataService: IDataService = JsonDataService()) =
            ReadStoryViewModel(story, dataService)
    }

    sealed interface State {
        data object Loading : State
        data class Success(val page: HtmlPage) : State
        data class Error(val message: String) : State
    }

    private val service = ReadStoryService(dataService = dataService)

    private val _state = MutableStateFlow<State>(State.Loading)
    val state: StateFlow<State> = _state.asStateFlow()

    private val _readerScreen = MutableStateFlow<ReaderScreen>(ReaderScreen.Reading(page = 0, total = 1))
    val readerScreen: StateFlow<ReaderScreen> = _readerScreen.asStateFlow()

    private val _reachedEnd = MutableStateFlow(false)
    val reachedEnd: StateFlow<Boolean> = _reachedEnd.asStateFlow()

    private var cachedSuggestions: List<StorySuggestion>? = null
    private var lastReadingScreen = ReaderScreen.Reading(page = 0, total = 1)
    private var loaded = false

    fun loadIfNeeded() {
        if (loaded) return
        loaded = true

        viewModelScope.launch {
            _state.value = State.Loading
            _state.value = try {
                State.Success(service.getContentFor(story))
            } catch (e: Exception) {
                State.Error(e.message ?: "Unknown error")
            }
        }
    }

    // Fed by the WebView polling the in-page paginator's window.getCurrentPage()/getPageCount().
    // Ignored once the reader has moved past Reading (webview is no longer on screen by then).
    fun onPageInfoChanged(page: Int, total: Int) {
        if (_readerScreen.value is ReaderScreen.Reading) {
            _readerScreen.value = ReaderScreen.Reading(page, total)
        }
    }

    // Suggestions are fetched ahead of time so the "read more" button can be
    // hidden entirely when there's nothing to suggest, rather than opening
    // onto an empty screen.
    fun onReachedEnd() {
        if (_reachedEnd.value) return

        viewModelScope.launch {
            val suggestions = try {
                service.getSuggestionBy(story)
            } catch (e: Exception) {
                emptyList()
            }
            cachedSuggestions = suggestions
            if (suggestions.isNotEmpty()) {
                _reachedEnd.value = true
            }
        }
    }

    fun showSuggestions() {
        val current = _readerScreen.value as? ReaderScreen.Reading ?: return
        lastReadingScreen = current

        _readerScreen.value = ReaderScreen.LoadingSuggestions
        viewModelScope.launch {
            _readerScreen.value = try {
                ReaderScreen.Finished(cachedSuggestions ?: service.getSuggestionBy(story))
            } catch (e: Exception) {
                current
            }
        }
    }

    // Lets the top bar's Back button step out of the suggestions view without
    // popping the nav backstack, since suggestions aren't a separate route.
    fun dismissSuggestions() {
        if (_readerScreen.value is ReaderScreen.Finished) {
            _readerScreen.value = lastReadingScreen
        }
    }
}