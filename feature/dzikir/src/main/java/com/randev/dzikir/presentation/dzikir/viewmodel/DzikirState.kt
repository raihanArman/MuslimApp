package com.randev.dzikir.presentation.dzikir.viewmodel

import com.randev.dzikir.domain.model.Dzikir

/**
 * @author Raihan Arman
 * @date 10/07/24
 */
data class DzikirState(
    val isLoading: Boolean = false,
    val errorMessage: String ? = null,
    val data: List<Dzikir> ? = null,
)
