package com.raydev.dailyduas

/**
 * @author Raihan Arman
 * @date 20/05/24
 */
interface DailyDuasFirestoreService {
    suspend fun getDailyDuas(): List<DailyDuasResponse>
}
