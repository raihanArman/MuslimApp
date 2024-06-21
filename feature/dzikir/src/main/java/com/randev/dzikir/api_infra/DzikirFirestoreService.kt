package com.randev.dzikir.api_infra

/**
 * @author Raihan Arman
 * @date 18/06/24
 */
interface DzikirFirestoreService {
    suspend fun getDzikir(category: String): List<DzikirResponse>
    suspend fun getDzikirPriority(): List<DzikirPriorityResponse>
}
