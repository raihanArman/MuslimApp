package com.raydev.data.network

import com.raydev.shared.model.City
import com.raydev.shared.response.ResponseSholat
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PrayerService {

    @GET("sholat/kota/cari/{city}")
    suspend fun searchCity(
        @Path("city") city: String
    ): ResponseSholat<List<City>>

}