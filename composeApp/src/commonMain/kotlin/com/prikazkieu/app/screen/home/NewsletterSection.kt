package com.prikazkieu.app.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prikazkieu.app.customComponent.WavesDivider

@Composable
fun NewsletterSection() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFDFF0F5))
        ) {
            WavesDivider(
                modifier = Modifier.fillMaxWidth(),
                wave1Color = Color(0xFFB8DFF0),
                wave2Color = Color(0xFF6BAEAD),
                wave3Color = Color(0xFF3D8C8A)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF3D8C8A)),
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
        // Watermark
        Text(
            text = "ПРИКАЗКА",
            style = TextStyle(
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White.copy(alpha = 0.25f)
            ),
            modifier = Modifier.align(Alignment.Center)
        )

        // Italic label
        Text(
            text = "по пощата в сряда",
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
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = "",
            onValueChange = { },
            placeholder = {
                Text(
                    text = "E-mail адрес",
                    style = TextStyle(
                        fontStyle = FontStyle.Italic,
                        fontSize = 18.sp,
                        color = Color.Gray
                    )
                )
            },
            modifier = Modifier
                .weight(1f)
                .height(56.dp),
            shape = RectangleShape,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                unfocusedBorderColor = Color.White,
                focusedBorderColor = Color.Black
            ),
            singleLine = true
        )

        Button(
            onClick = { },
            modifier = Modifier
                .height(56.dp)
                .width(72.dp)
                .padding(start = 4.dp),
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFE8C84A)
            ),
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(
                text = "ok",
                style = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A1A1A)
                )
            )
        }
    }
}

@Composable
private fun NewsletterDisclaimer() {
    Text(
        text = "С натискането на бутона \"OK\", се съгласявате с нашите условия.",
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