package com.prikazkieu.app.ui.screen.authors

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.prikazkieu.app.data.model.Story
import com.prikazkieu.app.ui.components.FilterSection
import com.prikazkieu.app.ui.components.StoriesListSection
import com.prikazkieu.app.ui.viewmodel.StoriesListViewModel
import com.prikazkieu.app.ui.viewmodel.StoriesQuery

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthorStoriesScreen(
    authorName: String,
    onStoryClick: (Story) -> Unit,
    showFilterSheet: Boolean = false,
    onFilterDismiss: () -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: StoriesListViewModel = remember { StoriesListViewModel.forAuthor(authorName) }
) {
    val filterMask by viewModel.filterMask.collectAsState()

    StoriesListSection(
        query = StoriesQuery.ByAuthor(authorName),
        onStoryClick = onStoryClick,
        modifier = modifier.fillMaxSize(),
        viewModel = viewModel
    )

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