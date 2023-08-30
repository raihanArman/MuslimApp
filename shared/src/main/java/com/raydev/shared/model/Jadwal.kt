package com.raydev.shared.model

import com.google.gson.annotations.SerializedName

data class Jadwal(

    @SerializedName("ashar")
    val ashar: String,

    @SerializedName("date")
    val date: String,

    @SerializedName("dhuha")
    val dhuha: String,

    @SerializedName("dzuhur")
    val dzuhur: String,

    @SerializedName("imsak")
    val imsak: String,

    @SerializedName("isya")
    val isya: String,

    @SerializedName("maghrib")
    val maghrib: String,

    @SerializedName("subuh")
    val subuh: String,

    @SerializedName("tanggal")
    val tanggal: String,

    @SerializedName("terbit")
    val fajr: String
)
