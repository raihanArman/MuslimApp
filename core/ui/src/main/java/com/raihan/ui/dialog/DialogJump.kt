package com.raihan.ui.dialog

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.raihan.ui.text_field.TextFieldCustom

/**
 * @author Raihan Arman
 * @date 31/08/23
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DialogJump(
    modifier: Modifier = Modifier,
    onDismissDialog: () -> Unit,
    onPositiveClick: () -> Unit,
    textFieldValue: String,
    textFieldPlaceHolderValue: String,
    textMessage: String,
    onTextFieldChange: (String) -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    Dialog(onDismissRequest = onDismissDialog) {
        Surface(
            modifier = modifier,
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Pergi ke Ayat",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                content()
                Text(text = textMessage)
                TextFieldCustom(
                    modifier = Modifier.width(150.dp),
                    value = textFieldValue,
                    onValueChange = onTextFieldChange,
                    textPlaceHolder = textFieldPlaceHolderValue,
                    keyboardType = KeyboardType.Number
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    TextButton(onClick = onDismissDialog) {
                        Text(text = "Batal")
                    }
                    TextButton(onClick = onPositiveClick) {
                        Text(text = "Pergi")
                    }
                }
            }
        }
    }
}
