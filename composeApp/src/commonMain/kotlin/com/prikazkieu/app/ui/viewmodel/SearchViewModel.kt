package com.prikazkieu.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prikazkieu.app.data.model.SearchResult
import com.prikazkieu.app.data.service.IDataService
import com.prikazkieu.app.data.service.JsonDataService
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val dataService: IDataService = JsonDataService()
) : ViewModel() {

    data class Section(val title: String, val results: List<SearchResult>)

    sealed interface State {
        data object Idle : State
        data object Loading : State
        data class Results(val sections: List<Section>) : State
        data class Error(val message: String) : State
    }

    private val _state = MutableStateFlow<State>(State.Idle)
    val state: StateFlow<State> = _state.asStateFlow()

    private var debounceJob: Job? = null

    fun onQueryChanged(query: String) {
        debounceJob?.cancel()
        if (query.isBlank()) {
            _state.value = State.Idle
            return
        }
        _state.value = State.Loading
        debounceJob = viewModelScope.launch {
            delay(2000)
            try {
                val results = dataService.search(query)
                _state.value = State.Results(groupIntoSections(results))
            } catch (e: Exception) {
                _state.value = State.Error(e.message ?: "Unknown error")
            }
        }
    }

    private fun groupIntoSections(results: List<SearchResult>): List<Section> {
        val sections = mutableListOf<Section>()
        results.filterIsInstance<SearchResult.StoryResult>()
            .takeIf { it.isNotEmpty() }
            ?.let { sections.add(Section("Приказки", it)) }
        results.filterIsInstance<SearchResult.AuthorResult>()
            .takeIf { it.isNotEmpty() }
            ?.let { sections.add(Section("Автори", it)) }
        results.filterIsInstance<SearchResult.KingdomResult>()
            .takeIf { it.isNotEmpty() }
            ?.let { sections.add(Section("Царства", it)) }
        return sections
    }
}