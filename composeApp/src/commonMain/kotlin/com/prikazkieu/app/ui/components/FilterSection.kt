package com.prikazkieu.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prikazkieu.app.data.model.Filter

private val filterGroups = listOf(
    "Времетраене" to listOf(
        Filter.READ_MINUTES_2,
        Filter.READ_MINUTES_10,
        Filter.READ_MINUTES_30,
        Filter.READ_MINUTES_99,
        Filter.READ_MINUTES_100
    ),
    "Вид" to listOf(
        Filter.STORY_TYPE_FAIRY_TALES,
        Filter.STORY_TYPE_FOLK_TALES,
        Filter.STORY_TYPE_MYTHS_AND_LEGENDS,
        Filter.STORY_TYPE_FABLES,
        Filter.STORY_TYPE_POEMS_AND_SONGS,
        Filter.STORY_TYPE_RIDDLES,
        Filter.STORY_TYPE_DID_YOU_KNOW
    ),
    "Формат" to listOf(
        Filter.STORY_FORMAT_TEXT,
        Filter.STORY_FORMAT_AUDIO,
        Filter.STORY_FORMAT_VIDEO
    ),
    "Възрастова група" to listOf(
        Filter.AGE_GROUP_3,
        Filter.AGE_GROUP_5,
        Filter.AGE_GROUP_9,
        Filter.AGE_GROUP_17
    )
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilterSection(
    filterMask: Int,
    onApply: (Int) -> Unit
) {
    var localMask by remember(filterMask) { mutableIntStateOf(filterMask) }

    Column(modifier = Modifier.fillMaxWidth().navigationBarsPadding()) {
        // Scrollable filter groups — weight(fill=false) means: use natural height if it fits,
        // cap at available space and scroll if it overflows
        Column(
            modifier = Modifier
                .weight(1f, fill = false)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                text = "Филтри",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                color = Color(0xFFA52A2A),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            filterGroups.forEach { (groupTitle, filters) ->
                Text(
                    text = groupTitle,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    filters.forEach { filter ->
                        val selected = (localMask and filter.mask) != 0
                        FilterChip(
                            selected = selected,
                            onClick = {
                                localMask = if (selected) {
                                    localMask and filter.mask.inv()
                                } else {
                                    localMask or filter.mask
                                }
                            },
                            label = { Text(filter.displayName, fontSize = 12.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color(0xFFA52A2A),
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }
            }
        }

        // Sticky action buttons
        HorizontalDivider()
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            OutlinedButton(
                onClick = { localMask = 0 },
                modifier = Modifier.weight(1f),
                border = BorderStroke(1.dp, Color(0xFFA52A2A)),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFA52A2A))
            ) {
                Text("Изчисти", fontFamily = FontFamily.Serif)
            }
            Button(
                onClick = { onApply(localMask) },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA52A2A))
            ) {
                Text("Приложи", color = Color.White, fontFamily = FontFamily.Serif)
            }
        }
    }
}