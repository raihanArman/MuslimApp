package com.raihan.ui.tile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raihan.ui.button.ButtonReadMore
import com.raydev.shared.util.SharedFont

/**
 * @author Raihan Arman
 * @date 10/07/24
 */
@Composable
fun TileDzikir(
    modifier: Modifier = Modifier,
    content: String,
    title: String,
    translate: String,
    times: String
) {
    var isExpanded by remember {
        mutableStateOf(false)
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
                    .background(Color.White)
            ) {
                DzikirContent(
                    title = title,
                    content = content
                )
                Spacer(modifier = Modifier.height(16.dp))
//                Text(
//                    text = times,
//                    fontSize = 14.sp,
//                    fontWeight = FontWeight.Bold,
//                    modifier = Modifier.fillMaxWidth(),
//                    textAlign = TextAlign.Center
//                )
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

                ButtonReadMore(isExpanded = isExpanded) {
                    isExpanded = !isExpanded
                }
            }
        }
    }
}

@Composable
fun ColumnScope.DzikirContent(title: String, content: String) {
    Text(
        text = title,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
    )
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = content,
        fontSize = 18.sp,
        color = Color.Black,
        fontFamily = FontFamily(
            Font(SharedFont.uthman)
        )
    )
}
