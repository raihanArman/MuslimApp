package com.raydev.data.network

import com.raydev.shared.model.Ayah
import com.raydev.shared.model.Surah
import retrofit2.http.GET
import retrofit2.http.Path

interface QuranService {
    @GET("99c279bb173a6e28359c/data")
    suspend fun getListSurah(): List<Surah>

    @GET("99c279bb173a6e28359c/surat/{nomor}")
    suspend fun getListAyat(
        @Path("nomor") nomor: String
    ): List<Ayah>
}