package com.raydev.shared.model

import com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("id")
    val id: String?,

    @SerializedName("lokasi")
    val location: String?
)
