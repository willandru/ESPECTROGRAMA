package com.example.espectrograma

import androidx.compose.foundation.Canvas
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
            .height(250.dp)
    ) {

        if (samples.isEmpty()) {
            return@Canvas
        }

        val centerY = size.height / 2f

        val xStep = size.width / samples.size.toFloat()

        var lastX = 0f
        var lastY = centerY

        for (i in samples.indices) {

            val x = i * xStep

            val normalized =
                samples[i] / 32768f

            val y =
                centerY -
                        normalized * centerY

            if (i > 0) {

                drawLine(
                    color = Color.Green,
                    start = Offset(lastX, lastY),
                    end = Offset(x, y),
                    strokeWidth = 2f
                )

            }

            lastX = x
            lastY = y

        }

    }

}