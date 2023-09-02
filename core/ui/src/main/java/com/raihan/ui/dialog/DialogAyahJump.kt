package com.raihan.ui.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.raydev.shared.model.Surah

/**
 * @author Raihan Arman
 * @date 29/08/23
 */
@Composable
fun DialogAyahJump(
    modifier: Modifier = Modifier,
    surah: Surah,
    onDismissDialog: () -> Unit,
    onPositiveClick: (Int) -> Unit
) {
    var textFieldValue by remember {
        mutableStateOf("")
    }

    DialogJump(
        modifier = modifier,
        onDismissDialog = onDismissDialog,
        onPositiveClick = {
            if (textFieldValue.isNotEmpty()) {
                onPositiveClick(textFieldValue.toInt())
            }
        },
        textFieldValue = textFieldValue,
        textFieldPlaceHolderValue = "1 - ${surah.verses}",
        textMessage = "Masukkan ayah dari 1 - ${surah.verses}",
        onTextFieldChange = { value ->
            setValueAyahInput(value, surah.verses) {
                textFieldValue = value
            }
        }
    ) {
    }
}
