package com.raihanarman.qiblah

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.util.Calendar
import java.util.Date
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * @author Raihan Arman
 * @date 16/09/23
 */
@Composable
fun CompassView(
    modifier: Modifier = Modifier,
    time: () -> Long,
    circleRadius: Float,
    outerCircleThickness: Float
) {
    var circleCenter by remember {
        mutableStateOf(Offset.Zero)
    }

    Box(modifier = modifier) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height
            circleCenter = Offset(x = width / 2f, y = height / 2f)
            val date = Date(time())
            val cal = Calendar.getInstance()
            cal.time = date

            drawCircle(
                style = Stroke(
                    width = outerCircleThickness
                ),
                brush = Brush.linearGradient(
                    listOf(
                        Color.White.copy(alpha = 0.45f),
                        Color.DarkGray.copy(alpha = 0.35f)
                    )
                ),
                radius = circleRadius + outerCircleThickness / 2f,
                center = circleCenter
            )

            drawCircle(
                brush = Brush.radialGradient(
                    listOf(
                        Color.White.copy(alpha = 0.45f),
                        Color.DarkGray.copy(alpha = 0.25f)
                    )
                ),
                radius = circleRadius,
                center = circleCenter
            )
            drawCircle(
                color = Color.Gray,
                radius = 15f,
                center = circleCenter
            )

//            val littleLineLength = circleRadius * 0.1f
//            val largeLineLength = circleRadius * 0.2f

            for (i in 0 until 60) {
                val angelInDegrees = i * 360f / 60
                val angelInRad = angelInDegrees * PI / 180f + PI / 2f
//                val lineLength = if (i % 5 == 0) largeLineLength else littleLineLength
//                val lineThickness = if (i % 5 == 0) 5f else 2f

                val start = Offset(
                    x = (circleRadius * cos(angelInRad) + circleCenter.x).toFloat(),
                    y = (circleRadius * sin(angelInRad) + circleCenter.y).toFloat()
                )

//                val end = Offset(
//                    x = (circleRadius * cos(angelInRad) + circleCenter.x).toFloat(),
//                    y = (circleRadius * sin(angelInRad) + lineLength + circleCenter.y).toFloat()
//                )

                rotate(
                    angelInDegrees,
                    pivot = start
                ) {
                }
            }
        }
    }
}

@Preview
@Composable
fun TestCompassView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        CompassView(
            modifier = Modifier
                .size(500.dp),
            time = {
                System.currentTimeMillis()
            },
            circleRadius = 600f,
            outerCircleThickness = 50f
        )
    }
}
