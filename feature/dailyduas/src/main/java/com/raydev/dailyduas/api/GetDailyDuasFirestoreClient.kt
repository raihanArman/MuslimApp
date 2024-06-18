package com.raydev.dailyduas.api

import com.raydev.anabstract.state.FirestoreClientResult
import kotlinx.coroutines.flow.Flow

/**
 * @author Raihan Arman
 * @date 20/05/24
 */
interface GetDailyDuasFirestoreClient {
    fun getDailyDuas(): Flow<FirestoreClientResult<List<DailyDuasModel>>>
}
