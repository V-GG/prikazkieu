package com.prikazkieu.app.ui.screen.authors

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.prikazkieu.app.data.model.Author
import com.prikazkieu.app.ui.components.AuthorCard
import com.prikazkieu.app.ui.viewmodel.AuthorViewModel

private val BulgarianAlphabet = listOf(
    "А","Б","В","Г","Д","Е","Ж","З","И","Й",
    "К","Л","М","Н","О","П","Р","С","Т","У",
    "Ф","Х","Ц","Ч","Ш","Щ","Ъ","Ь","Ю","Я"
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AuthorsScreen(
    onAuthorClick: (Author) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AuthorViewModel = viewModel(
        factory = viewModelFactory { initializer { AuthorViewModel() } }
    )
) {
    val state by viewModel.state.collectAsState()
    val selectedLetter by viewModel.selectedLetter.collectAsState()
    var alphabetExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadAuthors()
    }

    when (val s = state) {
        is AuthorViewModel.State.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color(0xFFA52A2A))
            }
        }

        is AuthorViewModel.State.Success -> {
            val filtered = if (selectedLetter == null) s.authors
                else s.authors.filter { it.name.startsWith(selectedLetter!!, ignoreCase = true) }

            Column(modifier = modifier.fillMaxSize()) {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    maxLines = if (alphabetExpanded) Int.MAX_VALUE else 1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    BulgarianAlphabet.forEach { letter ->
                        val selected = letter == selectedLetter
                        FilterChip(
                            selected = selected,
                            onClick = {
                                viewModel.selectLetter(if (selected) null else letter)
                            },
                            label = {
                                Text(
                                    text = letter,
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily.Serif
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color(0xFFA52A2A),
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { alphabetExpanded = !alphabetExpanded }
                        .padding(vertical = 2.dp)
                ) {
                    Icon(
                        imageVector = if (alphabetExpanded) Icons.Default.KeyboardArrowUp
                                      else Icons.Default.KeyboardArrowDown,
                        contentDescription = if (alphabetExpanded) "Collapse" else "Expand",
                        tint = Color(0xFFA52A2A)
                    )
                }

                HorizontalDivider()

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filtered, key = { it.name }) { author ->
                        AuthorCard(
                            author = author,
                            onAuthorClick = onAuthorClick,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }

        is AuthorViewModel.State.Error -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = s.message, color = Color(0xFFA52A2A), fontSize = 14.sp)
            }
        }
    }
}