package com.raihan.ui.button

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * @author Raihan Arman
 * @date 01/08/24
 */
@Composable
fun ButtonReadMore(
    modifier: Modifier = Modifier,
    isExpanded: Boolean,
    onClick: () -> Unit
) {
    val rotate = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = isExpanded) {
        rotate.animateTo(targetValue = if (!isExpanded) 0f else 180f)
    }

    TextButton(modifier = modifier, onClick = onClick) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (isExpanded) "Tutup" else "Artinya",
                color = Color.Gray,
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                modifier = Modifier
                    .rotate(degrees = rotate.value)
            )
        }
    }
}
