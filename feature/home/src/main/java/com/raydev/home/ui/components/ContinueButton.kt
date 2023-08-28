package com.raydev.home.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.raihan.ui.button.ButtonRoundedPrimary

/**
 * @author Raihan Arman
 * @date 26/08/23
 */
@Composable
fun ContinueButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    ButtonRoundedPrimary(
        modifier = modifier,
        text = "Lanjutkan",
        onClick = onClick
    )
}
