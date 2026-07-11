package com.prikazkieu.app.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prikazkieu.app.ui.components.WavesDivider

@Composable
fun NewsletterSection() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            WavesDivider(
                modifier = Modifier.fillMaxWidth(),
                wave1Color = Color(0xFF1B2E5E),
                wave2Color = Color(0xFF2E6AAF),
                wave3Color = Color(0xFFB8DFF0)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFB8DFF0),
                            Color(0xFF3D8C8A),
                        )
                    )
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            NewsletterTitle()
            NewsletterEmailField()
            NewsletterDisclaimer()
        }
    }
}

@Composable
private fun NewsletterTitle() {
    Box(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "ПРИКАЗКА",
            style = TextStyle(
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White.copy(alpha = 0.25f)
            ),
            modifier = Modifier.align(Alignment.Center)
        )

        Text(
            text = "в сряда",
            style = TextStyle(
                fontStyle = FontStyle.Italic,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                color = Color.White
            ),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 2.dp)
        )
    }
}

@Composable
private fun NewsletterEmailField() {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = { },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFEDD82A)
            )
        ) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Абонирай се",
                style = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily.Serif,
                    color = Color.Black
                )
            )
        }
    }
}

@Composable
private fun NewsletterDisclaimer() {
    Text(
        text = "С натискането на бутона \"Абонирай се\", се съгласявате с нашите условия.",
        style = TextStyle(
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            color = Color.White,
            lineHeight = 16.sp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )
}