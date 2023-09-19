package com.raydev.home.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raydev.domain.model.MosqueData

/**
 * @author Raihan Arman
 * @date 19/09/23
 */
@Composable
fun TileMosque(
    modifier: Modifier = Modifier,
    mosque: MosqueData
) {
    Box(
        modifier = modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column {
                Text(
                    text = mosque.title,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "${mosque.distance} dari lokasi anda",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }
        }
    }
}
