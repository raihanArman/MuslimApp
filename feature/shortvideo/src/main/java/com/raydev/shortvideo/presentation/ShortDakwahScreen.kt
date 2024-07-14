package com.raydev.shortvideo.presentation

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.navigation.NavGraphBuilder
import com.raydev.navigation.AppNavigator
import com.raydev.navigation.Destination
import com.raydev.navigation.composable
import org.koin.androidx.compose.get
import org.koin.androidx.compose.getViewModel

/**
 * @author Raihan Arman
 * @date 30/06/24
 */

val DarkBlue = Color(0XFF141721)
val DarkPink = Color(0XFF2C121B)

fun NavGraphBuilder.shortDakwahNavigation() = kotlin.run {
    composable(Destination.ShortDakwahScreen) {
        val navigator: AppNavigator = get<AppNavigator>()
        ShortDakwahScreen(navigator)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShortDakwahScreen(navigator: AppNavigator) {
    val viewModel: GetShortVideoViewModel = getViewModel()
    val state by viewModel.uiState.collectAsState()

    val pagerState = rememberPagerState(initialPage = 1 ?: 0)
    val coroutineScope = rememberCoroutineScope()
    val localDensity = LocalDensity.current
    val fling = PagerDefaults.flingBehavior(
        state = pagerState,
        lowVelocityAnimationSpec = tween(
            easing = LinearEasing, durationMillis = 300
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.horizontalGradient(
                    listOf(DarkPink, DarkBlue)
                )
            )
    ) {
        state.data?.let { data ->
            VerticalPager(
                pageCount = data.size,
                state = pagerState,
                flingBehavior = fling,
                beyondBoundsPageCount = 1,
                modifier = Modifier.fillMaxSize()
            ) {
                ShortItem(
                    video = data[it],
                    pageIndex = it,
                    pagerState = pagerState,
                    onShare = {
                    }
                )
            }

            IconButton(onClick = {
                navigator.tryNavigateBack()
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }
}
