package com.raydev.dailyduas.domain

import kotlinx.coroutines.flow.Flow

/**
 * @author Raihan Arman
 * @date 20/05/24
 */

sealed class FirestoreDomainResult {
    data class Success(val root: List<DailyDuas>) : FirestoreDomainResult()
    data class Failure(val exception: Exception) : FirestoreDomainResult()
}

interface GetDuasUseCase {
    fun getDailyDuas(): Flow<FirestoreDomainResult>
}
