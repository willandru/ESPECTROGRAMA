package com.example.espectrograma

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun WaveformView(
    samples: ShortArray,
    modifier: Modifier = Modifier
) {

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
            .border(
                width = 2.dp,
                color = Color.Gray
            )
            .background(Color.Black)
    ) {

        if (samples.isEmpty()) return@Canvas

        val centerY = size.height / 2f

        val xStep = size.width / (samples.size - 1).toFloat()

        drawLine(
            color = Color.DarkGray,
            start = Offset(0f, centerY),
            end = Offset(size.width, centerY),
            strokeWidth = 1f
        )

        var previous = Offset(0f, centerY)

        for (i in samples.indices) {

            val normalized = samples[i] / 32768f

            val current = Offset(
                x = i * xStep,
                y = centerY - normalized * centerY * 0.9f
            )

            if (i > 0) {
                drawLine(
                    color = Color(0xFF00FF00),
                    start = previous,
                    end = current,
                    strokeWidth = 2f
                )
            }

            previous = current
        }
    }
}