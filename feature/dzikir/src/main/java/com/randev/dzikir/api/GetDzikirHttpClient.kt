package com.randev.dzikir.api

import com.raydev.anabstract.state.FirestoreClientResult
import kotlinx.coroutines.flow.Flow

/**
 * @author Raihan Arman
 * @date 18/06/24
 */
interface GetDzikirHttpClient {
    fun getDzikir(request: DzikirRequestDto): Flow<FirestoreClientResult<List<DzikirModel>>>
}
