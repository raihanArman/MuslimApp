package com.raydev.data.repository

import com.google.android.gms.maps.model.LatLng
import com.raydev.anabstract.middleware.NetworkResource
import com.raydev.anabstract.state.ResponseState
import com.raydev.data.mapper.mapToMosqueModel
import com.raydev.data.network.HereService
import com.raydev.domain.model.MosqueModel
import com.raydev.domain.repository.MosqueRepository
import kotlinx.coroutines.flow.Flow

/**
 * @author Raihan Arman
 * @date 18/09/23
 */
class MosqueRepositoryImpl(
    private val hereService: HereService
) : MosqueRepository {
    override fun getNearbyMosque(
        coordinate: LatLng,
        limit: Int
    ): Flow<ResponseState<MosqueModel>> {
        return object : NetworkResource<MosqueModel>() {
            override suspend fun remoteFetch(): MosqueModel {
                val result = hereService.searchMosque(
                    coordinate = "${coordinate.latitude},${coordinate.longitude}",
                    limit = limit
                )

                return result.mapToMosqueModel()
            }
        }.asFlow()
    }
}
