package com.raihan.ui.button

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * @author Raihan Arman
 * @date 26/08/23
 */
@Composable
fun ButtonRoundedPrimary(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    icon: (@Composable RowScope.() -> Unit) ? = null
) {
    Box(
        modifier = modifier
            .height(30.dp)
    ) {
        Button(
            modifier = Modifier
                .padding(0.dp),
            onClick = onClick,
            shape = RoundedCornerShape(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
        ) {
            Row(
                modifier = Modifier
            ) {
                Text(
                    text = text,
                    fontSize = 8.sp,
                    color = Color.White
                )
                icon?.let {
                    it()
                }
            }
        }
    }
}
