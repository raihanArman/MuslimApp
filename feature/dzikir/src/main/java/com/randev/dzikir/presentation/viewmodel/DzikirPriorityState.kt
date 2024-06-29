package com.randev.dzikir.presentation.viewmodel

import com.randev.dzikir.domain.model.DzikirPriority

/**
 * @author Raihan Arman
 * @date 29/06/24
 */
data class DzikirPriorityState(
    val isLoading: Boolean = false,
    val errorMessage: String ? = null,
    val data: List<DzikirPriority> ? = null,
)
