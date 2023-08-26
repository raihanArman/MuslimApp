package com.raihan.ui.bottom_sheet

import androidx.compose.foundation.background
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/**
 * @author Raihan Arman
 * @date 26/08/23
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseBottomSheet(onDismiss: () -> Unit, content: @Composable () -> Unit) {
    val modalBottomSheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        containerColor = Color.White,
    ) {
        content.invoke()
    }
}
