package com.raydev.shortvideo.api_infra

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

/**
 * @author Raihan Arman
 * @date 14/07/24
 */
interface ShortVideoService {
    suspend fun getShortVideo(): List<ShortVideoResponse>
}

class ShortVideoServiceImpl : ShortVideoService {
    private val firestore = FirebaseFirestore.getInstance()
    override suspend fun getShortVideo(): List<ShortVideoResponse> {
        val snapshot = firestore.collection("short_video").get().await()
        val response = snapshot.toObjects(ShortVideoResponse::class.java)
        return response
    }
}
