package com.prikazkieu.app.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prikazkieu.app.customComponent.WavesDivider
import org.jetbrains.compose.resources.painterResource
import prikazkieu.composeapp.generated.resources.Res
import prikazkieu.composeapp.generated.resources.jaba_pisatel
import prikazkieu.composeapp.generated.resources.jaba_queen
import prikazkieu.composeapp.generated.resources.ornamental_title_big_yellow

@Composable
fun BrowsePrikazkiSection() {
    Column(modifier = Modifier.fillMaxWidth()) {
        WavesDivider(
            modifier = Modifier.fillMaxWidth(),
            wave1Color = Color(0xFF1B2E5E),
            wave2Color = Color(0xFF2E6AAF),
            wave3Color = Color(0xFFB8DFF0)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFB8DFF0),
                            Color(0xFFDFF0F5),
                        )
                    )
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OrnamentalHeader()

            Spacer(modifier = Modifier.height(48.dp))

            AuthorRow()
            KingdomRow()
        }
    }
}

@Composable
private fun OrnamentalHeader() {
    Box(modifier = Modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(Res.drawable.ornamental_title_big_yellow),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop
        )

        Text(
            text = "Избери си Приказка",
            style = TextStyle(
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = Color(0xFF2C1810),
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 32.dp)
        )
    }
}

@Composable
private fun AuthorRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        BigCategoryButton(
            text = "АВТОР",
            modifier = Modifier.align(Alignment.Bottom),
            onClick = { }
        )
        Image(
            painter = painterResource(Res.drawable.jaba_pisatel),
            contentDescription = null,
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
private fun KingdomRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(Res.drawable.jaba_queen),
            contentDescription = null,
            contentScale = ContentScale.Fit
        )
        BigCategoryButton(
            text = "ЦАРСТВО",
            onClick = { }
        )
    }
}

@Composable
private fun BigCategoryButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        shape = RectangleShape,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        contentPadding = PaddingValues(0.dp),
        elevation = null
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontSize = 48.sp,
                fontWeight = FontWeight.Black,
                color = Color.White,
                shadow = Shadow(
                    color = Color(0xFFB0B8C0),
                    offset = Offset(6f, 8f),
                    blurRadius = 4f
                )
            )
        )
    }
}