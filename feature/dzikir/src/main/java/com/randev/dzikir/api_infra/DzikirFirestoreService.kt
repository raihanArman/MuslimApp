package com.randev.dzikir.api_infra

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

/**
 * @author Raihan Arman
 * @date 18/06/24
 */
interface DzikirFirestoreService {
    suspend fun getDzikir(category: String): List<DzikirResponse>
    suspend fun getDzikirPriority(): List<DzikirPriorityResponse>
}

class DzikirFirestoreServiceImpl : DzikirFirestoreService {
    private val firestore = FirebaseFirestore.getInstance()
    override suspend fun getDzikir(category: String): List<DzikirResponse> {
        val snapshot = firestore.collection("dzikir").get().await()
        val response = snapshot.toObjects(DzikirResponse::class.java)
        return response
    }

    override suspend fun getDzikirPriority(): List<DzikirPriorityResponse> {
        val snapshot = firestore.collection("dzikir_priority").get().await()
        val response = snapshot.toObjects(DzikirPriorityResponse::class.java)
        return response
    }
}
