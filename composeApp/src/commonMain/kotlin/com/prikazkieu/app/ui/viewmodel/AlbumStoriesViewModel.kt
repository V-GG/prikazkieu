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

class AlbumStoriesViewModel(
    private val dataService: IDataService = JsonDataService()
) : ViewModel() {

    sealed interface State {
        data object Loading : State
        data class Success(val stories: List<Story>) : State
        data class Error(val message: String) : State
    }

    private val _state = MutableStateFlow<State>(State.Loading)
    val state: StateFlow<State> = _state.asStateFlow()

    fun loadStoriesByAlbum(album: String) {
        viewModelScope.launch {
            _state.value = State.Loading
            try {
                val stories = dataService.getStoriesByAlbum(album)
                _state.value = State.Success(stories)
            } catch (e: Exception) {
                _state.value = State.Error(e.message ?: "Unknown error")
            }
        }
    }
}
