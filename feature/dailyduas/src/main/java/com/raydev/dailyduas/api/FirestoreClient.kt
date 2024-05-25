package com.raydev.dailyduas.api

import kotlinx.coroutines.flow.Flow

/**
 * @author Raihan Arman
 * @date 20/05/24
 */
sealed class FirestoreClientResult {
    data class Success(val root: List<DailyDuasModel>) : FirestoreClientResult()
    data class Failure(val exception: Exception) : FirestoreClientResult()
}

interface FirestoreClient {
    fun getDailyDuas(): Flow<FirestoreClientResult>
}
