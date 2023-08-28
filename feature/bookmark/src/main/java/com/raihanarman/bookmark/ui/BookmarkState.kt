package com.raihanarman.bookmark.ui

import com.raydev.shared.model.BookmarkQuran

/**
 * @author Raihan Arman
 * @date 26/08/23
 */
data class BookmarkState(
    val isLoading: Boolean = false,
    val isEmpty: Boolean = false,
    val data: List<BookmarkQuran>? = null
)
