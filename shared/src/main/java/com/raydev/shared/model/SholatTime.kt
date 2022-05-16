package com.raydev.shared.model

import com.google.gson.annotations.SerializedName

data class SholatTime(

    @SerializedName("daerah")
    val disctrict: String,

    @SerializedName("id")
    val id: String,

    @SerializedName("jadwal")
    val schedule: Jadwal,

    @SerializedName("koordinat")
    val coordinat: Koordinat,

    @SerializedName("lokasi")
    val location: String
)