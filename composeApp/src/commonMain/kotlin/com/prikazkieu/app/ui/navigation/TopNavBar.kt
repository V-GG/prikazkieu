package com.prikazkieu.app.ui.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Article
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.NotificationImportant
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prikazkieu.app.ui.viewmodel.LatestStoriesViewModel
import org.jetbrains.compose.resources.painterResource
import prikazkieu.composeapp.generated.resources.Res
import prikazkieu.composeapp.generated.resources.prikazki_logo

@Composable
fun TopNavBar(
    state: NavBarState = NavBarState(),
    onBack: () -> Unit = {},
    onClose: () -> Unit = {},
    onBlogClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    onFilterClick: () -> Unit = {},
    onLatestClick: () -> Unit = {},
    latestStoriesViewModel: LatestStoriesViewModel = remember { LatestStoriesViewModel() }
) {
    LaunchedEffect(Unit) {
        latestStoriesViewModel.loadIfNeeded()
    }

    val latestCount = (latestStoriesViewModel.state.collectAsState().value as?
        LatestStoriesViewModel.State.Success)?.stories?.size ?: 0
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            if (state.showClose) {
                IconButton(onClick = onClose) {
                    Icon(Icons.Filled.Close, contentDescription = "Close")
                }
            }

            if (state.showBack) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            }

            if (state.showPrikazkiLogo) {
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
                    Image(
                        painter = painterResource(Res.drawable.prikazki_logo),
                        contentDescription = "Prikazki",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.height(36.dp)
                    )
                }
            }

            if (state.showSearch) {
                Box(modifier = Modifier.weight(1f)) {
                    OutlinedTextField(
                        state = TextFieldState(),
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        leadingIcon = {
                            Icon(Icons.Default.Search, contentDescription = "Search")
                        }
                    )
                    Box(modifier = Modifier.matchParentSize().clickable { onSearchClick() })
                }
            }

            if (state.showDefault) {
                IconButton(onClick = onBlogClick) {
                    Icon(Icons.AutoMirrored.Filled.Article, contentDescription = "Blog")
                }
                Box {
                    IconButton(onClick = onLatestClick) {
                        Icon(Icons.Filled.NotificationImportant, contentDescription = "Latest")
                    }
                    if (latestCount > 0) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(top = 4.dp, end = 4.dp)
                                .size(16.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFA52A2A))
                        ) {
                            Text(
                                text = latestCount.toString(),
                                color = Color.White,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            if (state.showFilter) {
                IconButton(onClick = onFilterClick) {
                    Icon(Icons.Filled.FilterList, contentDescription = "Filter")
                }
            }
        }
    }
}
