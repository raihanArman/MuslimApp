package com.raydev.data.mapper

import com.google.android.gms.maps.model.LatLng
import com.raydev.data.model.response.HereDiscoverResponse
import com.raydev.domain.model.MosqueData
import com.raydev.domain.model.MosqueModel

/**
 * @author Raihan Arman
 * @date 18/09/23
 */
fun HereDiscoverResponse.mapToMosqueModel(): MosqueModel {
    val listMosque = items?.map {
        MosqueData(
            title = it?.title.orEmpty(),
            distance = "${it?.distance ?: 0} m",
            subDistrict = it?.address?.subdistrict.orEmpty(),
            district = it?.address?.district.orEmpty(),
            coordinate = LatLng(it?.position?.lat ?: 0.0, it?.position?.lng ?: 0.0)
        )
    }
    return MosqueModel(
        data = listMosque ?: emptyList()
    )
}
