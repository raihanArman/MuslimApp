package com.raihanarman.splash

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraphBuilder
import com.raydev.navigation.Destination
import com.raydev.navigation.composable
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharedFlow
import org.koin.androidx.compose.getViewModel

/**
 * @author Raihan Arman
 * @date 06/08/23
 */

fun NavGraphBuilder.splashNavigation() = run {
    composable(Destination.SplashScreen){
        val viewModel: SplashViewModel = getViewModel()
        val event = viewModel.observeEvent
        SplashScreen(
            onEvent = viewModel::onEvent,
            event = event,
            onNavigate = {
                viewModel.goToDashboard()
            }
        )
    }
}

@Composable
fun SplashScreen(
    onEvent: (SplashEvent) -> Unit,
    event: SharedFlow<SplashEvent>?,
    onNavigate: () -> Unit
) {
    LaunchedEffect(key1 = "splash") {
        delay(10000)
        onEvent(SplashEvent.OnNavigateToMain)

        event?.collect {
            when(it) {
                SplashEvent.Initial -> {}
                SplashEvent.OnNavigateToMain -> onNavigate()
            }
        }

    }
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Ini Splash Screen",
            color = Color.Black,
            fontSize = 50.sp
        )
    }
}