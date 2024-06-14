package com.raihan.ui.tile

import androidx.compose.animation.Animatable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raydev.shared.util.SharedFont

/**
 * @author Raihan Arman
 * @date 22/05/24
 */
@Composable
fun TileDailyDuas(
    modifier: Modifier = Modifier,
    title: String,
    content: String,
    translate: String,
    isExpanded: Boolean,
    onClick: () -> Unit
) {
    val rotate = remember { Animatable(0f) }

    LaunchedEffect(key1 = isExpanded) {
        rotate.animateTo(
            targetValue = if (!isExpanded) 0f else 180f
        )
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(Color.White),
        shape = RoundedCornerShape(10.dp)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 5.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = title, fontSize = 16.sp, color = Color.Black)
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        modifier = Modifier
                            .rotate(degrees = rotate.value)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                AnimatedVisibility(visible = isExpanded) {
                    DuasContent(content = content, translate = translate)
                }
            }
        }
    }
}

@Composable
fun DuasContent(modifier: Modifier = Modifier, content: String, translate: String) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = content,
            fontSize = 18.sp,
            color = Color.Black,
            textAlign = TextAlign.End,
            fontFamily = FontFamily(
                Font(SharedFont.uthman)
            )
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = translate,
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.End,
        )
    }
}
