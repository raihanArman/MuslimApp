package com.randev.dzikir.domain

import com.raydev.anabstract.state.FirestoreDomainResult
import kotlinx.coroutines.flow.Flow

/**
 * @author Raihan Arman
 * @date 29/06/24
 */
interface GetDzikirPriorityUseCase {
    fun load(): Flow<FirestoreDomainResult<List<DzikirPriority>>>
}
