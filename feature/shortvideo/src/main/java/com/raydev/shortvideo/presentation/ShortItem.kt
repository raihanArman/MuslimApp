package com.raydev.shortvideo.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raydev.player.VideoPlayer
import com.raydev.shortvideo.R
import com.raydev.shortvideo.domain.ShortVideo

/**
 * @author Raihan Arman
 * @date 11/07/24
 */
@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
fun ShortItem(
    modifier: Modifier = Modifier,
    video: ShortVideo,
    pageIndex: Int,
    pagerState: PagerState,
    onShare: (ShortVideo) -> Unit
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }

    var pauseButtonVisibility by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.TopEnd) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            VideoPlayer(
                url = video.url,
                pagerState = pagerState,
                pageIndex = pageIndex,
                onSingleTap = {
                    pauseButtonVisibility = it.isPlaying
                    it.playWhenReady = !it.isPlaying
                },
                onDoubleTap = { it, data ->
                },
                onVideoDispose = {
                    pauseButtonVisibility = false
                },
                onVideoGoBackground = {
                    pauseButtonVisibility = false
                }
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = video.title,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = video.description,
                    color = Color.White,
                    fontSize = 12.sp,
                    maxLines = if (!isExpanded) 2 else 5,
                    modifier = Modifier
                        .animateContentSize(
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioLowBouncy,
                                stiffness = Spring.StiffnessLow
                            )
                        )
                        .clickable {
                            isExpanded = !isExpanded
                        },
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        AnimatedVisibility(
            visible = pauseButtonVisibility,
            enter = scaleIn(spring(Spring.DampingRatioMediumBouncy), initialScale = 1.5f),
            exit = scaleOut(tween(150)),
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_play),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(36.dp)
            )
        }

        IconButton(onClick = {
            onShare(video)
        }) {
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}
