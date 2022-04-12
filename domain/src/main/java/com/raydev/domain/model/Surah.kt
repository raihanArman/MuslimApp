package com.raydev.domain.model

import com.google.gson.annotations.SerializedName

data class Surah(
    @SerializedName("arti")
    var arti: String,

    @SerializedName("asma")
    var asma: String,

    @SerializedName("ayat")
    var ayat: String,

    @SerializedName("nama")
    var nama: String,

    @SerializedName("type")
    var type: String,

    @SerializedName("audio")
    var audio: String,

    @SerializedName("nomor")
    var nomor: String,

    @SerializedName("keterangan")
    var keterangan: String
)
