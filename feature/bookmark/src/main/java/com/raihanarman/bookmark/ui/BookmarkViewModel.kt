package com.raihanarman.bookmark.ui

import com.raydev.anabstract.base.BaseViewModel
import com.raydev.anabstract.state.ResponseState
import com.raydev.domain.repository.BookmarkRepository
import com.raydev.navigation.AppNavigator
import com.raydev.navigation.Destination
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * @author Raihan Arman
 * @date 26/08/23
 */
class BookmarkViewModel(
    private val repository: BookmarkRepository,
    private val appNavigator: AppNavigator
): BaseViewModel() {

    private val _state: MutableStateFlow<BookmarkState> = MutableStateFlow(BookmarkState())
    val state = _state.asStateFlow()

    private val _event: MutableSharedFlow<BookmarkEvent> = MutableSharedFlow()
    val event = _event.asSharedFlow()

    init {
        getBookmarks()
    }

    private fun getBookmarks() {
        launch {
            repository.getBookmarks().collect { response ->
                println("ResponseBookmark -> $response")
                when(response) {
                    ResponseState.Empty -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                isEmpty = true
                            )
                        }
                    }
                    is ResponseState.Loading -> {
                        _state.update {
                            it.copy(
                                isLoading = true,
                                isEmpty = false,
                            )
                        }
                    }
                    is ResponseState.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                isEmpty = false,
                                data = response.data
                            )
                        }
                    }

                    else -> {}
                }
            }
        }
    }

    fun onEvent(event: BookmarkEvent) {
        launch {
            when(event) {
                BookmarkEvent.Initial -> {}
                BookmarkEvent.OnBack -> {
                    appNavigator.navigateBack()
                }
                is BookmarkEvent.OnNavigateToReadQuran -> {
                    appNavigator.tryNavigateTo(Destination.ReadQuranScreen(
                        event.surahId - 1,
                        event.ayahId
                    ))
                }
            }
        }
    }

}