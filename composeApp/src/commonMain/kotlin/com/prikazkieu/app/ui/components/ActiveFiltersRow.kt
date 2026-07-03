package com.prikazkieu.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prikazkieu.app.data.model.Filter

@Composable
fun ActiveFiltersRow(
    filterMask: Int,
    onRemove: (Filter) -> Unit,
    onClearAll: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    if (filterMask == 0) return

    val activeFilters = Filter.entries.filter { it.mask and filterMask != 0 }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        OutlinedIconButton(
            onClick = onClearAll,
            colors = IconButtonDefaults.outlinedIconButtonColors(contentColor = Color(0xFFA52A2A)),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFA52A2A)),
            modifier = Modifier.padding(start = 12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Clear all filters",
                modifier = Modifier.size(18.dp)
            )
        }

        LazyRow(
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            items(activeFilters, key = { it.name }) { filter ->
                InputChip(
                    selected = true,
                    onClick = { onRemove(filter) },
                    label = { Text(filter.displayName, fontSize = 12.sp) },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Remove ${filter.displayName}",
                            modifier = Modifier.size(14.dp)
                        )
                    },
                    colors = InputChipDefaults.inputChipColors(
                        selectedContainerColor = Color(0xFFA52A2A),
                        selectedLabelColor = Color.White,
                        selectedTrailingIconColor = Color.White
                    )
                )
            }
        }
    }
}