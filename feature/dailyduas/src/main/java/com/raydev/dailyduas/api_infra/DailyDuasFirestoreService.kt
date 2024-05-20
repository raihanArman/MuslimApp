package com.raydev.dailyduas.api_infra

import com.raydev.dailyduas.api_infra.DailyDuasResponse

/**
 * @author Raihan Arman
 * @date 20/05/24
 */
interface DailyDuasFirestoreService {
    suspend fun getDailyDuas(): List<DailyDuasResponse>
}
