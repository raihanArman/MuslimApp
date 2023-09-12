package com.raihanarman.qiblah

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateFloatAsState
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
import kotlin.math.atan2

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
    var magnetometerData by remember { mutableStateOf(FloatArray(3)) }
    var accelerometerData by remember { mutableStateOf(FloatArray(3)) }
    var azimuth by remember { mutableStateOf(0f) }
    val animatedAzimuth by animateFloatAsState(targetValue = -azimuth, label = "")
    val azimuthThreshold = 5.0

    val sensorManager = LocalView.current.context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    val magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
    val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    fun getAzimuth(): Float {
        val rotationMatrix = FloatArray(9)
        val inclinationMatrix = FloatArray(9)
        val orientationValues = FloatArray(3)

        SensorManager.getRotationMatrix(rotationMatrix, inclinationMatrix, accelerometerData, magnetometerData)
        SensorManager.getOrientation(rotationMatrix, orientationValues)

        // Calculate azimuth (Qibla direction)
        val azimuthRadians = atan2(orientationValues[0], orientationValues[2])
        return Math.toDegrees(azimuthRadians.toDouble()).toFloat()
    }

    fun updateAzimuth() {
        val newAzimuth = getAzimuth()
        val azimuthChange = Math.abs(newAzimuth - oldAzimuth)
        if (azimuthChange >= azimuthThreshold) {
            // Azimuth change is significant; update the compass or trigger actions
            // Update previousAzimuth with newAzimuth
            println("AMAMAMAMAMA -> updateAzimuth")
            oldAzimuth = newAzimuth
            azimuth = newAzimuth
            // Perform actions or update the UI as needed
        }
    }

    DisposableEffect(Unit) {
        val magnetometerListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                event?.values?.copyInto(magnetometerData)
                updateAzimuth()
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        val accelerometerListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                event?.values?.copyInto(accelerometerData)
                updateAzimuth()
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        sensorManager.registerListener(
            magnetometerListener,
            magnetometer,
            SensorManager.SENSOR_DELAY_NORMAL
        )
        sensorManager.registerListener(
            accelerometerListener,
            accelerometer,
            SensorManager.SENSOR_DELAY_NORMAL
        )

        onDispose {
            sensorManager.unregisterListener(magnetometerListener)
            sensorManager.unregisterListener(accelerometerListener)
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
                        .rotate(animatedAzimuth),
                    color = Color.Black
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
//            Text(
//                text = "Your Location: ${userLocation.latitude}, ${userLocation.longitude}",
//                style = TextStyle(fontSize = 16.sp)
//            )
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
