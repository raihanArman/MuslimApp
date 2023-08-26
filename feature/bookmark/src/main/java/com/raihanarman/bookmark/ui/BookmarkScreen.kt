package com.raihanarman.bookmark.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.raihanarman.bookmark.ui.components.HeaderBookmark
import com.raihanarman.bookmark.ui.components.TileBookmark
import com.raydev.navigation.Destination
import com.raydev.navigation.composable
import org.koin.androidx.compose.getViewModel

/**
 * @author Raihan Arman
 * @date 26/08/23
 */
fun NavGraphBuilder.bookmarkNavigation() = run {
    composable(Destination.BookmarkScreen){
        val viewModel: BookmarkViewModel = getViewModel()
        val event by viewModel.event.collectAsState(BookmarkEvent.Initial)
        val state by viewModel.state.collectAsState()
        BookmarkScreen(
            onEvent = viewModel::onEvent,
            event = event,
            state =  state
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BookmarkScreen(
    event: BookmarkEvent,
    state: BookmarkState,
    onEvent: (BookmarkEvent) -> Unit
) {
    Scaffold(
        topBar = {
            HeaderBookmark {
                onEvent(BookmarkEvent.OnBack)
            }
        },
        content = {
            Box(
                modifier = Modifier.fillMaxSize().padding(top = it.calculateTopPadding()),
                contentAlignment = Alignment.Center
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator()
                }

                if (state.isEmpty) {
                    Text(text = "Bookmark Kosong", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    state.data?.let {
                        items(it) {
                            TileBookmark(bookmarkQuran = it)
                        }
                    }
                }
            }
        }
    )
}