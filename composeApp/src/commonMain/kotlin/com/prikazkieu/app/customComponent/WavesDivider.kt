package com.prikazkieu.app.customComponent

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp

@Composable
fun WavesDivider(
    modifier: Modifier = Modifier,
    wave1Color: Color,
    wave2Color: Color,
    wave3Color: Color,
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
    ) {
        val width = size.width
        val height = size.height

        // --- Wave 1 — dark navy (back layer) ---
        val wave1 = Path().apply {
            moveTo(0f, height * 0.4f)
            cubicTo(
                width * 0.25f, height * 0.1f,   // control point 1
                width * 0.5f,  height * 0.6f,   // control point 2
                width * 0.75f, height * 0.3f    // end point
            )
            cubicTo(
                width * 0.88f, height * 0.15f,
                width,         height * 0.4f,
                width,         height * 0.4f
            )
            lineTo(width, height)
            lineTo(0f, height)
            close()
        }
        drawPath(wave1, color = wave1Color)

        // --- Wave 2 — medium blue (middle layer) ---
        val wave2 = Path().apply {
            moveTo(0f, height * 0.5f)
            cubicTo(
                width * 0.25f, height * 0.75f,
                width * 0.5f,  height * 0.25f,
                width * 0.75f, height * 0.55f
            )
            cubicTo(
                width * 0.88f, height * 0.7f,
                width,         height * 0.5f,
                width,         height * 0.5f
            )
            lineTo(width, height)
            lineTo(0f, height)
            close()
        }
        drawPath(wave2, color = wave2Color)

        // --- Wave 3 — light blue (front layer) ---
        val wave3 = Path().apply {
            moveTo(0f, height * 0.7f)
            cubicTo(
                width * 0.25f, height * 0.45f,
                width * 0.5f,  height * 0.85f,
                width * 0.75f, height * 0.65f
            )
            cubicTo(
                width * 0.88f, height * 0.55f,
                width,         height * 0.7f,
                width,         height * 0.7f
            )
            lineTo(width, height)
            lineTo(0f, height)
            close()
        }
        drawPath(wave3, color = wave3Color)
    }
}