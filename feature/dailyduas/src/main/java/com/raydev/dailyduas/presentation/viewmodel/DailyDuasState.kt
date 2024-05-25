package com.raydev.dailyduas.presentation.viewmodel

import com.raydev.dailyduas.domain.DailyDuas

/**
 * @author Raihan Arman
 * @date 21/05/24
 */
data class DailyDuasState(
    val isLoading: Boolean = false,
    val errorMessage: String ? = null,
    val data: List<DailyDuas> ? = null
)
