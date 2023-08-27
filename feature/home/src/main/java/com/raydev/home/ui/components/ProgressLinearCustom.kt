package com.raydev.home.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

/**
 * @author Raihan Arman
 * @date 25/08/23
 */
@Composable
fun ProgressLinearCustom(
    modifier: Modifier = Modifier,
    progress: Float,
    progressColor: Color = MaterialTheme.colorScheme.primary,
    backgroundColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
    clipShape: Shape = RoundedCornerShape(16.dp)
) {
    var initialProgress by remember {
        mutableStateOf(0f)
    }

    LaunchedEffect(key1 = true) {
        println("AMPAAASSS KUDAA -> progress $progress")
        initialProgress = progress
    }

    val progressAnimation by animateFloatAsState(targetValue = initialProgress, animationSpec = tween(1500),
        label = ""
    )

    Box(
        modifier = modifier
            .clip(clipShape)
            .background(backgroundColor)
            .fillMaxWidth()
            .height(8.dp)
    ) {
        Box(
            modifier = Modifier
                .background(progressColor)
                .fillMaxHeight()
                .fillMaxWidth(progressAnimation)
        )
    }
}