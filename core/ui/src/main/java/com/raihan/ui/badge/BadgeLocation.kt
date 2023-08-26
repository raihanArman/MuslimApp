package com.raihan.ui.badge

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * @author Raihan Arman
 * @date 20/08/23
 */
@Composable
fun BadgeLocation(
    modifier: Modifier = Modifier,
    location: String
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary)
    ) {
       Row(
           modifier = Modifier
               .padding(horizontal = 8.dp, vertical = 5.dp)
       ) {
           Icon(
               modifier = Modifier.size(15.dp),
               imageVector = Icons.Outlined.LocationOn,
               contentDescription = null,
               tint = Color.White
           )
           Spacer(modifier = Modifier.width(5.dp))
           Text(
               text = location,
               color = Color.White,
               fontSize = 10.sp
           )
       }
    }
}