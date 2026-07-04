package com.prikazkieu.app.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.prikazkieu.app.data.model.Album

private val CardSurface       = Color(0xFFFFFCF5)
private val CardBorder        = Color(0xFFE3D5B8)
private val TitleColor        = Color(0xFF3B2E1E)
private val DescriptionColor  = Color(0xFF7A6A52)
private val AccentRed         = Color(0xFFA32D2D)

@Composable
fun AlbumCard(
    album: Album,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = { onClick(album.name) },
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CardSurface),
        border = BorderStroke(1.dp, CardBorder)
    ) {
        Row(modifier = Modifier.height(IntrinsicSize.Min)) {
            AsyncImage(
                model = album.image,
                contentDescription = null,
                modifier = Modifier.width(96.dp).fillMaxHeight().padding(2.dp),
                contentScale = ContentScale.Fit
            )
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    album.name,
                    style = MaterialTheme.typography.titleSmall,
                    color = TitleColor
                )
                Text(
                    album.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = DescriptionColor,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.weight(1f))
                Text(
                    "${album.storyCount} приказки",
                    style = MaterialTheme.typography.labelSmall,
                    color = AccentRed
                )
            }
        }
    }
}