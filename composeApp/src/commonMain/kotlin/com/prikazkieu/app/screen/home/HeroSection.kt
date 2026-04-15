package com.prikazkieu.app.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prikazkieu.app.customComponent.AgeControl

@Composable
fun HeroSection() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Избери си",
            style = TextStyle(
                fontSize = 28.sp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Light,
                color = Color(0xFFADB5BD),
                fontFamily = FontFamily.Serif
            )
        )

        Text(
            text = "ПРИКАЗКА",
            style = TextStyle(
                fontSize = 56.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFFFFB627),
                shadow = Shadow(
                    color = Color(0xFF5C3A00),
                    offset = Offset(4f, 6f),
                    blurRadius = 2f
                )
            )
        )

        Spacer(modifier = Modifier.height(24.dp))
        AgeControl()
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "1500+ прекрасни класически произведения за четене, слушане и гледане – за малки и пораснали любители на вълшебни истории от целия свят.",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                color = Color.White,
                textAlign = TextAlign.Center,
                lineHeight = 32.sp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(48.dp))
    }
}