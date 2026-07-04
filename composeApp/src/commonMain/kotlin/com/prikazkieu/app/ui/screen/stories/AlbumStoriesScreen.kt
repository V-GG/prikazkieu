package com.prikazkieu.app.ui.screen.stories

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.prikazkieu.app.data.model.Story
import com.prikazkieu.app.ui.components.ActiveFiltersRow
import com.prikazkieu.app.ui.components.FilterSection
import com.prikazkieu.app.ui.components.StoriesListSection
import com.prikazkieu.app.ui.viewmodel.StoriesListViewModel
import com.prikazkieu.app.ui.viewmodel.StoriesQuery

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumStoriesScreen(
    albumName: String,
    onStoryClick: (Story) -> Unit,
    showFilterSheet: Boolean = false,
    onFilterDismiss: () -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: StoriesListViewModel = viewModel(
        key = "album_$albumName",
        factory = viewModelFactory { initializer { StoriesListViewModel.forAlbum(albumName) } }
    )
) {
    val filterMask by viewModel.filterMask.collectAsState()

    Column(modifier = modifier.fillMaxSize()) {
        ActiveFiltersRow(
            filterMask = filterMask,
            onRemove = { filter -> viewModel.setFilterMask(filterMask and filter.mask.inv()) },
            onClearAll = { viewModel.setFilterMask(0) }
        )
        StoriesListSection(
            query = StoriesQuery.ByAlbum(albumName),
            onStoryClick = onStoryClick,
            modifier = Modifier.weight(1f),
            viewModel = viewModel
        )
    }

    if (showFilterSheet) {
        ModalBottomSheet(
            onDismissRequest = onFilterDismiss,
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ) {
            FilterSection(
                filterMask = filterMask,
                onApply = { mask ->
                    viewModel.setFilterMask(mask)
                    onFilterDismiss()
                }
            )
        }
    }
}