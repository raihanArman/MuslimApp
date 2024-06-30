package com.raihan.ui.tile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raydev.shared.util.SharedFont

/**
 * @author Raihan Arman
 * @date 30/06/24
 */
@Composable
fun TileDzikirPriority(
    modifier: Modifier = Modifier,
    content: String,
    translate: String
) {
    val rotate = remember { Animatable(0f) }
    var isExpanded by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = isExpanded) {
        rotate.animateTo(targetValue = if (!isExpanded) 0f else 180f)
    }

    Card(
        modifier = modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(Color.White),
        shape = RoundedCornerShape(10.dp)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 5.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = content,
                    fontSize = 14.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    fontFamily = FontFamily(
                        Font(SharedFont.uthman)
                    )
                )
                AnimatedVisibility(visible = isExpanded) {
                    Column {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = translate,
                            color = Color.Gray,
                            fontSize = 12.sp
                        )
                    }
                }
                TextButton(onClick = { isExpanded = !isExpanded }) {
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
        }
    }
}
