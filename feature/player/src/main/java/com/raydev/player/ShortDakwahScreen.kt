package com.raydev.player

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.raydev.navigation.Destination
import com.raydev.navigation.composable
import com.raydev.player.model.VideoModel

/**
 * @author Raihan Arman
 * @date 30/06/24
 */

val DarkBlue = Color(0XFF141721)
val DarkPink = Color(0XFF2C121B)

val listMovie = listOf(
    VideoModel(
        videoUrl = "https://firebasestorage.googleapis.com/v0/b/muslim-app-a1ea8.appspot.com/o/videoplayback%20(1).mp4?alt=media&token=37599e8e-de1e-412b-ae56-60c2b9617a33"
    ),
    VideoModel(
        videoUrl = "https://firebasestorage.googleapis.com/v0/b/muslim-app-a1ea8.appspot.com/o/videoplayback%20(2).mp4?alt=media&token=97c17d21-d420-4b48-b775-9336b92e6a68"
    ),
    VideoModel(
        videoUrl = "https://firebasestorage.googleapis.com/v0/b/muslim-app-a1ea8.appspot.com/o/videoplayback%20(3).mp4?alt=media&token=2d43bde4-479c-4f70-b3eb-57a4239a9a83"
    ),
    VideoModel(
        videoUrl = "https://firebasestorage.googleapis.com/v0/b/muslim-app-a1ea8.appspot.com/o/videoplayback.mp4?alt=media&token=44aebd6c-48d9-439a-b40e-e0722e4669ec"
    ),

)

fun NavGraphBuilder.shortDakwahNavigation() = kotlin.run {
    composable(Destination.ShortDakwahScreen) {
//        val navigator: AppNavigator = get<AppNavigator>()
        ShortDakwahScreen()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShortDakwahScreen(modifier: Modifier = Modifier) {
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
        VerticalPager(
            pageCount = listMovie.size,
            state = pagerState,
            flingBehavior = fling,
            beyondBoundsPageCount = 1,
            modifier = modifier
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                VideoPlayer(
                    videoModel = listMovie[it],
                    pagerState = pagerState,
                    pageIndex = it,
                    onSingleTap = {},
                    onDoubleTap = { it, data ->
                    }
                )
            }
        }
    }
}
