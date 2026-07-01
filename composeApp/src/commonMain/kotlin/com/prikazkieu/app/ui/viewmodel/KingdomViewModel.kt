package com.prikazkieu.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prikazkieu.app.data.model.Kingdom
import com.prikazkieu.app.data.service.IDataService
import com.prikazkieu.app.data.service.JsonDataService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class KingdomViewModel(
    private val dataService: IDataService = JsonDataService()
) : ViewModel() {

    sealed interface State {
        data object Loading : State
        data class Success(
            val kingdoms: List<Kingdom>,
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
                val kingdoms = dataService.getKingdomsPaged(0, pageSize)
                _state.value = State.Success(kingdoms = kingdoms, hasMore = kingdoms.size == pageSize)
            } catch (e: Exception) {
                _state.value = State.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun loadNextPage() {
        val current = _state.value as? State.Success ?: return
        if (!current.hasMore || isLoadingMore) return
        isLoadingMore = true
        _state.value = current.copy(isLoadingMore = true)
        viewModelScope.launch {
            try {
                val nextPage = currentPage + 1
                val newKingdoms = dataService.getKingdomsPaged(nextPage, pageSize)
                currentPage = nextPage
                _state.value = State.Success(
                    kingdoms = current.kingdoms + newKingdoms,
                    hasMore = newKingdoms.size == pageSize
                )
            } catch (e: Exception) {
                _state.value = current.copy(isLoadingMore = false)
            } finally {
                isLoadingMore = false
            }
        }
    }
}