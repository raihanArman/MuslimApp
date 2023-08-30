package com.raihan.ui.app_bar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raydev.shared.util.SharedDrawable

/**
 * @author Raihan Arman
 * @date 26/08/23
 */
@Composable
fun AppBarCustom(
    modifier: Modifier = Modifier,
    title: String,
    onBack: (() -> Unit)? = null,
    action: @Composable RowScope.() -> Unit = {},
    content: (@Composable ColumnScope.() -> Unit)? = null,
    isCenter: Boolean = false
) {
    Surface(
        modifier = modifier,
        color = Color.White,
        shadowElevation = 2.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                onBack?.let {
                    IconButton(onClick = it) {
                        Icon(
                            painter = painterResource(id = SharedDrawable.baseline_arrow_back_24),
                            contentDescription = null
                        )
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    modifier = Modifier
                        .weight(1f),
                    text = title,
                    fontSize = 18.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    textAlign = if (isCenter) TextAlign.Center else TextAlign.Start
                )
                Spacer(modifier = Modifier.width(16.dp))
                action()
            }
            content?.let {
                Spacer(modifier = Modifier.height(10.dp))
                it()
            }
        }
    }
}
