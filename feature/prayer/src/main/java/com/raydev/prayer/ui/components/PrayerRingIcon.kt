package com.raydev.prayer.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.raydev.prayer.R

/**
 * @author Raihan Arman
 * @date 25/08/23
 */
@Composable
fun PrayerIconRing(isRing: Boolean, onClick: () -> Unit) {
    Icon(
        modifier = Modifier.clickable {
            onClick()
        },
        painter = painterResource(id = if (isRing) R.drawable.baseline_volume_up_24 else R.drawable.baseline_volume_off_24),
        contentDescription = null
    )
}