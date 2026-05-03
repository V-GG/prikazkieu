package com.prikazkieu.app.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontSynthesis
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prikazkieu.app.ui.components.NumberBadge
import com.prikazkieu.app.ui.components.WavesDivider
import org.jetbrains.compose.resources.painterResource
import prikazkieu.composeapp.generated.resources.Res
import prikazkieu.composeapp.generated.resources.ornamental_title_big_red

@Composable
fun FooterSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF3D8C8A))
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

            OrnamentalHeader()
            Spacer(modifier = Modifier.size(32.dp))

            Text(
                text = "Политически приказки",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color(0xFF8A8A82),
                letterSpacing = 0.5.sp,
                style = TextStyle(
                    fontSynthesis = FontSynthesis.None
                )
            )

            Spacer(modifier = Modifier.size(8.dp))

            NumberBadge(number = 42)

            Spacer(modifier = Modifier.size(32.dp))
        }
    }
}

@Composable
private fun OrnamentalHeader() {
    Box(modifier = Modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(Res.drawable.ornamental_title_big_red),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop
        )

        Text(
            text = "Най-новата Колекция",
            style = TextStyle(
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = Color(0xFFEDD82A),
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 32.dp)
        )
    }
}