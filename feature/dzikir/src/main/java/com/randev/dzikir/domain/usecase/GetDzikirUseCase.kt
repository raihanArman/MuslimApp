package com.randev.dzikir.domain.usecase

import com.randev.dzikir.domain.model.Dzikir
import com.randev.dzikir.domain.request.DzikirRequest
import com.raydev.anabstract.state.FirestoreDomainResult
import kotlinx.coroutines.flow.Flow

/**
 * @author Raihan Arman
 * @date 10/07/24
 */
interface GetDzikirUseCase {
    fun load(category: DzikirRequest): Flow<FirestoreDomainResult<List<Dzikir>>>
}
