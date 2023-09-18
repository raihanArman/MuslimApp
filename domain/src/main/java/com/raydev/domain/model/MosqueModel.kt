package com.raydev.domain.model

import com.google.android.gms.maps.model.LatLng
import com.raydev.anabstract.base.BaseModel

/**
 * @author Raihan Arman
 * @date 18/09/23
 */
data class MosqueModel(
    val data: List<MosqueData>
) : BaseModel()
data class MosqueData(
    val title: String,
    val district: String,
    val subDistrict: String,
    val distance: String,
    val coordinate: LatLng
)
