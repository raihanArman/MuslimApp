package com.raydev.dailyduas.domain

import com.raydev.anabstract.state.FirestoreDomainResult
import kotlinx.coroutines.flow.Flow

/**
 * @author Raihan Arman
 * @date 20/05/24
 */
interface GetDuasUseCase {
    fun getDailyDuas(): Flow<FirestoreDomainResult<List<DailyDuas>>>
}
