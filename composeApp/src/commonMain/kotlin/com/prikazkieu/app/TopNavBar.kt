package com.prikazkieu.app

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Article
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.CollectionsBookmark
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import prikazkieu.composeapp.generated.resources.Res
import prikazkieu.composeapp.generated.resources.prikazki_logo

@Composable
fun TopNavBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.safeDrawing),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Image(
            painter = painterResource(Res.drawable.prikazki_logo),
            contentDescription = "Prikazki Logo",
            modifier = Modifier.size(48.dp)
        )
        OutlinedTextField(
            shape = RoundedCornerShape(64.dp),
            state = TextFieldState(),
            modifier = Modifier.weight(1f).height(48.dp),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "Clear"
                )
            }
        )
        IconButton(onClick = { }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Article,
                contentDescription = "Blog"
            )
        }

        IconButton(onClick = { }) {
            Icon(
                imageVector = Icons.Default.CollectionsBookmark,
                contentDescription = "Collection"
            )
        }
    }
}