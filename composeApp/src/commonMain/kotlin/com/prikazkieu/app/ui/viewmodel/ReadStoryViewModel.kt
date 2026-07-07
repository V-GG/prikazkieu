package com.prikazkieu.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prikazkieu.app.data.model.HtmlPage
import com.prikazkieu.app.data.model.Story
import com.prikazkieu.app.data.service.IDataService
import com.prikazkieu.app.data.service.JsonDataService
import com.prikazkieu.app.data.service.ReadStoryService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

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

    private val _state = MutableStateFlow<State>(State.Loading)
    val state: StateFlow<State> = _state.asStateFlow()

    private var loaded = false

    fun loadIfNeeded() {
        if (loaded) return
        loaded = true

        viewModelScope.launch {
            _state.value = State.Loading
            _state.value = try {
                val service = ReadStoryService(dataService = dataService)
                State.Success(service.getContentFor(story))
            } catch (e: Exception) {
                State.Error(e.message ?: "Unknown error")
            }
        }
    }
}