package com.raydev.shared.model

import com.google.gson.annotations.SerializedName

data class Ayat(
    @SerializedName("ar")
    var arab: String,

    @SerializedName("id")
    var indo: String,

    @SerializedName("nomor")
    var nomor: String
)