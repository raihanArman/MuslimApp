package com.raydev.dailyduas.api_infra

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

/**
 * @author Raihan Arman
 * @date 20/05/24
 */
interface DailyDuasFirestoreService {
    suspend fun getDailyDuas(): List<DailyDuasResponse>
}

class DailyDuasFirestoreServiceImpl : DailyDuasFirestoreService {
    private val firestore = FirebaseFirestore.getInstance()
    override suspend fun getDailyDuas(): List<DailyDuasResponse> {
        val snapshot = firestore.collection("dailyduas").get().await()
        val response = snapshot.toObjects(DailyDuasResponse::class.java)
        return response
    }
}
