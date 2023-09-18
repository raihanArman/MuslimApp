package com.raihanarman.qiblah

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raihan.ui.bottom_sheet.BaseBottomSheet
import com.raihanarman.qiblah.util.rememberQibla
import com.raydev.shared.util.SharedDrawable

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
    var azimuth by remember {
        mutableStateOf(0f)
    }

    var qiblaDirection by remember {
        mutableStateOf(0f)
    }

    val animatedAzimuth by animateFloatAsState(targetValue = -azimuth, label = "")

    val qiblaHelper = rememberQibla(currentLatitude = currentLatitude) { newAzimuth, qibla ->
        azimuth = newAzimuth
        qiblaDirection = qibla
    }

    DisposableEffect(Unit) {
        onDispose {
            qiblaHelper.unregister()
        }
    }

    BaseBottomSheet(modifier = modifier, onDismiss = {
        onDismiss()
    }) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Compass(azimuth = animatedAzimuth, qiblaDirection = qiblaDirection)
        }
    }
}

@Composable
fun Compass(
    azimuth: Float,
    qiblaDirection: Float
) {
    Box(
        modifier = Modifier
            .size(200.dp)
            .rotate(azimuth),
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize(),
            painter = painterResource(id = SharedDrawable.ic_compassface),
            contentDescription = null
        )
        Image(
            modifier = Modifier
                .fillMaxSize()
                .rotate(qiblaDirection),
            painter = painterResource(id = SharedDrawable.ic_compass_arrow),
            contentDescription = null
        )
    }
//    Box(
//        modifier = Modifier
//            .size(200.dp)
//    ) {
//
//        Box(
//            modifier = Modifier
//                .size(200.dp)
//                .rotate(azimuth)
//        ) {
//            Text(
//                text = "Qibla",
//                color = Color.Black
//            )
    // Rotate the text based on the azimuth
//            Text(
//                text = "🕋",
//                modifier = Modifier
//                    .fillMaxSize()
//                    .rotate(qiblaDirection),
//                color = Color.Black
//            )
//        }
//    }
}

@Preview
@Composable
fun CompassPreview() {
    Compass(0f, 0f)
}
