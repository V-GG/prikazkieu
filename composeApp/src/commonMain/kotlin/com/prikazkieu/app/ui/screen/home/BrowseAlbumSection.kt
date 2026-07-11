package com.prikazkieu.app.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.prikazkieu.app.ui.components.AlbumCard
import com.prikazkieu.app.ui.components.WavesDivider
import com.prikazkieu.app.ui.viewmodel.AlbumViewModel
import org.jetbrains.compose.resources.painterResource
import prikazkieu.composeapp.generated.resources.Res
import prikazkieu.composeapp.generated.resources.ornamental_title_big_red

@Composable
fun BrowseAlbumSection(
    onAlbumClick: (String) -> Unit = {},
    viewModel: AlbumViewModel = viewModel(
        factory = viewModelFactory { initializer { AlbumViewModel() } }
    )
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) { viewModel.load() }

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF0EDD8)),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            WavesDivider(
                modifier = Modifier.fillMaxWidth(),
                wave1Color = Color(0xFFF0EDD8),
                wave2Color = Color(0xFFEDD82A),
                wave3Color = Color(0xFF3D8C8A),
                inverted = true
            )

            val albumCount = (state as? AlbumViewModel.State.Success)?.albums?.size
            OrnamentalHeader(count = albumCount)

            when (val s = state) {
                is AlbumViewModel.State.Loading -> {
                    CircularProgressIndicator(
                        color = Color(0xFFA52A2A),
                        modifier = Modifier.padding(32.dp)
                    )
                }

                is AlbumViewModel.State.Success -> {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        s.albums.forEach { album ->
                            AlbumCard(
                                album = album,
                                onClick = onAlbumClick
                            )
                        }
                    }
                }

                is AlbumViewModel.State.Error -> {
                    Text(
                        text = s.message,
                        color = Color(0xFFA52A2A),
                        fontSize = 14.sp,
                        modifier = Modifier.padding(32.dp)
                    )
                }
            }

            Box(modifier = Modifier.size(32.dp))
        }
    }
}

@Composable
private fun OrnamentalHeader(count: Int?) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(Res.drawable.ornamental_title_big_red),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop
        )

        Text(
            text = if (count != null) "$count най-нови колекции" else "колекции",
            style = TextStyle(
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = Color(0xFFEDD82A),
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 28.dp)
        )
    }
}