package com.raihanarman.qiblah

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import com.raihan.ui.bottom_sheet.BaseBottomSheet
import com.raihanarman.qiblah.util.QiblaHelper

/**
 * @author Raihan Arman
 * @date 12/09/23
 */

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun QiblaScreen(
    modifier: Modifier = Modifier,
    currentLatitude: Double,
    onDismiss: () -> Unit
) {
    var oldAzimuth = 0f

    var azimuth by remember { mutableStateOf(0f) }
    val animatedAzimuth by animateFloatAsState(targetValue = -azimuth, label = "", animationSpec = tween(800))
    val azimuthThreshold = 20.0

    var qiblaDirection by remember { mutableStateOf(0f) }

    val qiblaHelper = QiblaHelper(
        context = LocalView.current.context,
        currentLatitude = currentLatitude
    )

    fun updateAzimuth(newAzimuth: Float, qibla: Float) {
        val azimuthChange = Math.abs(newAzimuth - oldAzimuth)
        if (azimuthChange >= azimuthThreshold) {
            // Azimuth change is significant; update the compass or trigger actions
            // Update previousAzimuth with newAzimuth
            oldAzimuth = newAzimuth
            azimuth = newAzimuth

            // Perform actions or update the UI as needed
            qiblaDirection = qibla
        }
    }

    DisposableEffect(Unit) {
        val qiblaListener = object : QiblaHelper.QiblaListener {
            override fun onNewAzimuth(azimuth: Float, qiblaDirection: Float) {
                updateAzimuth(azimuth, qiblaDirection)
            }

            override fun onAccuracyChanged(accuracy: Int) {
            }
        }

        qiblaHelper.register()
        qiblaHelper.setListener(qiblaListener)

        onDispose {
            qiblaHelper.unregister()
        }
    }

    BaseBottomSheet(modifier = modifier, onDismiss = {
        onDismiss()
    }) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .background(Color.Gray, CircleShape)
                    .rotate(animatedAzimuth)
            ) {
                Text(
                    text = "Qibla",
                    color = Color.Black
                )
                // Rotate the text based on the azimuth
                Text(
                    text = "🕋",
                    modifier = Modifier
                        .fillMaxSize()
                        .rotate(qiblaDirection),
                    color = Color.Black
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun Compass(rotation: Float) {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        val centerX = canvasWidth / 2
        val centerY = canvasHeight / 2
        val needleLength = canvasWidth * 0.4f

        // Rotate canvas based on the current rotation angle
        rotate(rotation) {
            drawLine(
                color = Color.Black,
                start = Offset(centerX, centerY),
                end = Offset(centerX, centerY - needleLength),
                strokeWidth = 8f.dp.toPx()
            )
        }
    }
}
