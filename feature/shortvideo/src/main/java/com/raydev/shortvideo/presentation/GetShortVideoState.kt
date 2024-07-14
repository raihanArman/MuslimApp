package com.raydev.shortvideo.presentation

import com.raydev.shortvideo.domain.ShortVideo

/**
 * @author Raihan Arman
 * @date 14/07/24
 */
data class GetShortVideoState(
    val isLoading: Boolean = false,
    val errorMessage: String ? = null,
    val data: List<ShortVideo> ? = null,
)
