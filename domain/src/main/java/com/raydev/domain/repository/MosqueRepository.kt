package com.raydev.domain.repository

import com.google.android.gms.maps.model.LatLng
import com.raydev.anabstract.state.ResponseState
import com.raydev.domain.model.MosqueModel
import kotlinx.coroutines.flow.Flow

/**
 * @author Raihan Arman
 * @date 18/09/23
 */
interface MosqueRepository {
    fun getNearbyMosque(coordinate: LatLng, limit: Int): Flow<ResponseState<MosqueModel>>
}
