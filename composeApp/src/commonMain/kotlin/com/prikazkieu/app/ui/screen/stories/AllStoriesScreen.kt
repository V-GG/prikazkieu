package com.prikazkieu.app.ui.screen.stories

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import com.prikazkieu.app.data.model.Story
import com.prikazkieu.app.ui.components.FilterSection
import com.prikazkieu.app.ui.components.StoriesListSection
import com.prikazkieu.app.ui.viewmodel.StoriesListViewModel
import com.prikazkieu.app.ui.viewmodel.StoriesQuery
import org.jetbrains.compose.resources.painterResource
import prikazkieu.composeapp.generated.resources.Res
import prikazkieu.composeapp.generated.resources.leaf_textured

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllStoriesScreen(
    onStoryClick: (Story) -> Unit,
    showFilterSheet: Boolean = false,
    onFilterDismiss: () -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: StoriesListViewModel = remember { StoriesListViewModel.forAll() }
) {
    val filterMask by viewModel.filterMask.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(Res.drawable.leaf_textured),
                contentScale = ContentScale.Crop
            )
    ) {
        StoriesListSection(
            query = StoriesQuery.All,
            onStoryClick = onStoryClick,
            modifier = Modifier.fillMaxSize(),
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