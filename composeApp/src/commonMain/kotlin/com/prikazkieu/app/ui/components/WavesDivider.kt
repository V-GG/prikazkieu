package com.prikazkieu.app.ui.components

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
    inverted: Boolean = false
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
    ) {
        val width = size.width
        val height = size.height

        fun y(normal: Float) = if (inverted) 1f - normal else normal

        val wave1 = Path().apply {
            moveTo(0f, height * y(0.4f))
            cubicTo(
                width * 0.25f, height * y(0.1f),
                width * 0.5f,  height * y(0.6f),
                width * 0.75f, height * y(0.3f)
            )
            cubicTo(
                width * 0.88f, height * y(0.15f),
                width,         height * y(0.4f),
                width,         height * y(0.4f)
            )
            lineTo(width, if (inverted) 0f else height)
            lineTo(0f,   if (inverted) 0f else height)
            close()
        }
        drawPath(wave1, color = wave1Color)

        val wave2 = Path().apply {
            moveTo(0f, height * y(0.5f))
            cubicTo(
                width * 0.25f, height * y(0.75f),
                width * 0.5f,  height * y(0.25f),
                width * 0.75f, height * y(0.55f)
            )
            cubicTo(
                width * 0.88f, height * y(0.7f),
                width,         height * y(0.5f),
                width,         height * y(0.5f)
            )
            lineTo(width, if (inverted) 0f else height)
            lineTo(0f,   if (inverted) 0f else height)
            close()
        }
        drawPath(wave2, color = wave2Color)

        val wave3 = Path().apply {
            moveTo(0f, height * y(0.7f))
            cubicTo(
                width * 0.25f, height * y(0.45f),
                width * 0.5f,  height * y(0.85f),
                width * 0.75f, height * y(0.65f)
            )
            cubicTo(
                width * 0.88f, height * y(0.55f),
                width,         height * y(0.7f),
                width,         height * y(0.7f)
            )
            lineTo(width, if (inverted) 0f else height)
            lineTo(0f,   if (inverted) 0f else height)
            close()
        }
        drawPath(wave3, color = wave3Color)
    }
}
