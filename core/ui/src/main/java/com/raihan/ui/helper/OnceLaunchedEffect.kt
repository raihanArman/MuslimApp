package com.raihan.ui.helper

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.ViewModel

/**
 * @author Raihan Arman
 * @date 26/05/24
 */

@Composable
fun <VM : ViewModel> OnceLaunchedEffect(viewModel: VM, effect: suspend (VM) -> Unit, content: @Composable () -> Unit) {
    val isLaunched = rememberSaveable { mutableStateOf(false) }

    if (!isLaunched.value) {
        LaunchedEffect(Unit) {
            effect(viewModel)
            isLaunched.value = true
        }
    }

    content()
}
