package com.raihanarman.qiblah.util

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalView
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

/**
 * @author Raihan Arman
 * @date 16/09/23
 */
class QiblaHelper(
    context: Context,
    private val currentLatitude: Double
) : SensorEventListener {
    private var listener: QiblaListener? = null

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val gsensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    private val msensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

    private val mGravity = FloatArray(3)
    private val mGeomagnetic = FloatArray(3)
    private val r = FloatArray(9)
    private val i = FloatArray(9)

    private fun getAzimuth(event: SensorEvent): Float {
        val alpha = 0.97f
        var azimuth = 0f
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            mGravity[0] = alpha * mGravity[0] + (1 - alpha) * event.values[0]
            mGravity[1] = alpha * mGravity[1] + (1 - alpha) * event.values[1]
            mGravity[2] = alpha * mGravity[2] + (1 - alpha) * event.values[2]
        }
        if (event.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
            mGeomagnetic[0] = alpha * mGeomagnetic[0] + (1 - alpha) * event.values[0]
            mGeomagnetic[1] = alpha * mGeomagnetic[1] + (1 - alpha) * event.values[1]
            mGeomagnetic[2] = alpha * mGeomagnetic[2] + (1 - alpha) * event.values[2]
        }
        val success = SensorManager.getRotationMatrix(
            r, i, mGravity,
            mGeomagnetic
        )
        if (success) {
            val orientation = FloatArray(3)
            SensorManager.getOrientation(r, orientation)
            azimuth = Math.toDegrees(orientation[0].toDouble()).toFloat() // orientation
            azimuth = (azimuth + 0f + 360) % 360
        }

        return azimuth
    }

    private fun getQiblaDirection(): Float {
        val kaabaLng =
            39.826206 // ka'bah Position https://www.latlong.net/place/kaaba-mecca-saudi-arabia-12639.html
        val kaabaLat =
            Math.toRadians(21.422487) // ka'bah Position https://www.latlong.net/place/kaaba-mecca-saudi-arabia-12639.html
        val myLatRad = Math.toRadians(currentLatitude)
        val longDiff = Math.toRadians(kaabaLng - 107.446274)
        val y = sin(longDiff) * cos(kaabaLat)
        val x =
            cos(myLatRad) * sin(kaabaLat) - sin(myLatRad) * cos(kaabaLat) * cos(
                longDiff
            )
        return ((Math.toDegrees(atan2(y, x)) + 360) % 360).toFloat()
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            val azimuth = getAzimuth(it)
            val qiblaDirection = getQiblaDirection()
            listener?.onNewAzimuth(azimuth, qiblaDirection)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        if (sensor?.type == Sensor.TYPE_MAGNETIC_FIELD) {
            listener?.onAccuracyChanged(accuracy)
        }
    }

    fun register() {
        sensorManager.registerListener(
            this, gsensor,
            SensorManager.SENSOR_DELAY_GAME
        )
        sensorManager.registerListener(
            this, msensor,
            SensorManager.SENSOR_DELAY_GAME
        )
    }

    fun unregister() {
        sensorManager.unregisterListener(this)
        sensorManager.unregisterListener(this)
    }

    fun setListener(listener: QiblaListener?) {
        this.listener = listener
    }

    interface QiblaListener {
        fun onNewAzimuth(azimuth: Float, qiblaDirection: Float)
        fun onAccuracyChanged(accuracy: Int)
    }
}

@Composable
fun rememberQibla(
    currentLatitude: Double,
    value: (azimuth: Float, qibla: Float) -> Unit
): QiblaHelper {
    val context = LocalView.current.context
    return remember {
        val qiblaListener = object : QiblaHelper.QiblaListener {
            override fun onNewAzimuth(azimuth: Float, qiblaDirection: Float) {
                value(azimuth, qiblaDirection)
            }

            override fun onAccuracyChanged(accuracy: Int) {
                println("check accurate $accuracy")
            }
        }

        QiblaHelper(context = context, currentLatitude = currentLatitude).apply {
            register()
            setListener(qiblaListener)
        }
    }
}
