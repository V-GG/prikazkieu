package com.prikazkieu.app.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.prikazkieu.app.data.model.Author

@Composable
fun AuthorCard(
    author: Author,
    onAuthorClick: (Author) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = { onAuthorClick(author) },
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        shadowElevation = 4.dp,
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = author.name,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                color = Color(0xFFA52A2A),
                textAlign = TextAlign.Center,
                maxLines = 2
            )

            author.lived?.let {
                Spacer(Modifier.height(4.dp))
                Text(
                    text = it,
                    fontSize = 10.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(Modifier.height(8.dp))

            AsyncImage(
                model = author.image,
                contentDescription = author.name,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
            )

            author.origin?.let {
                Spacer(Modifier.height(6.dp))
                Text(
                    text = it,
                    fontSize = 10.sp,
                    color = Color.DarkGray,
                    textAlign = TextAlign.Center,
                    maxLines = 1
                )
            }
        }
    }
}