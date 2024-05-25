package com.raihan.ui.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * @author Raihan Arman
 * @date 25/09/23
 */
@Composable
fun CardMenu(
    modifier: Modifier = Modifier.width(50.dp),
    title: String,
    image: Int,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier.clickable {
            onClick()
        },
    ) {
        Card(
            modifier = Modifier
                .size(50.dp),
            shape = RoundedCornerShape(5.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(painter = painterResource(id = image), contentDescription = null)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = title,
            fontSize = 10.sp,
            textAlign = TextAlign.Center,
            color = Color.Black
        )
    }
}
