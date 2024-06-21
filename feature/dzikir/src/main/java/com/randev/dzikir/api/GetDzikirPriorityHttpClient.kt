package com.randev.dzikir.api

import com.raydev.anabstract.state.FirestoreClientResult
import kotlinx.coroutines.flow.Flow

/**
 * @author Raihan Arman
 * @date 21/06/24
 */
interface GetDzikirPriorityHttpClient {
    fun getDzikirPriority(): Flow<FirestoreClientResult<List<DzikirPriorityModel>>>
}
