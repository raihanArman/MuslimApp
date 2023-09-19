package com.raydev.domain.usecase

import com.google.android.gms.maps.model.LatLng
import com.raihanarman.location.LocationManager
import com.raydev.anabstract.state.ResponseState
import com.raydev.domain.model.MosqueModel
import com.raydev.domain.repository.MosqueRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest

/**
 * @author Raihan Arman
 * @date 19/09/23
 */
class GetNearbyMosqueUseCase(
    private val mosqueRepository: MosqueRepository
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<ResponseState<MosqueModel>> = LocationManager.instance
        .getLocationFlowEvent()
        .flatMapLatest {
            mosqueRepository.getNearbyMosque(LatLng(it.latitude, it.longitude), 5)
        }
}
