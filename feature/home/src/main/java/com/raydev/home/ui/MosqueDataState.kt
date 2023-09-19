package com.raydev.home.ui

import com.raydev.domain.model.MosqueData

/**
 * @author Raihan Arman
 * @date 19/09/23
 */
data class MosqueDataState(
    val mosqueData: List<MosqueData> ? = null,
    val isLoading: Boolean = false,
)
