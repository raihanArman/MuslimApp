package com.raihan.ui.tile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * @author Raihan Arman
 * @date 22/05/24
 */
@Composable
fun TileDailyDuas(
    modifier: Modifier = Modifier,
    title: String,
    content: String
) {
    var isShowContent by remember {
        mutableStateOf(false)
    }

    val icon = if (!isShowContent) {
        Icons.Default.KeyboardArrowDown
    } else {
        Icons.Default.KeyboardArrowUp
    }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp)),
        color = Color.Gray.copy(alpha = 0.3f),
        onClick = {
            isShowContent = !isShowContent
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = title, fontSize = 18.sp, color = Color.Black)
                Icon(
                    imageVector = icon,
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = content, fontSize = 18.sp, color = Color.Black)
        }
    }
}
