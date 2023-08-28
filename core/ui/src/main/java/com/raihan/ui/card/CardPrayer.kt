package com.raihan.ui.card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * @author Raihan Arman
 * @date 20/08/23
 */
@Composable
fun CardPrayer(
    modifier: Modifier = Modifier,
    time: String,
    prayer: String,
    isSelected: Boolean
) {
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primary
    else Color.White
    val contentColor = if (isSelected) Color.White
    else Color.Black
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp)),
        color = backgroundColor
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = time, color = contentColor)
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = prayer)
        }
    }
}
