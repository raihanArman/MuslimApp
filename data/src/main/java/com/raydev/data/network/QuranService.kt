package com.raydev.data.network

import com.raydev.domain.model.Ayat
import com.raydev.domain.model.Surah
import retrofit2.http.GET
import retrofit2.http.Path

interface QuranService {
    @GET("99c279bb173a6e28359c/data")
    suspend fun getListSurah(): List<Surah>

    @GET("9c279bb173a6e28359c/surat/{nomor}")
    suspend fun getListAyat(
        @Path("nomor") nomor: String
    ): List<Ayat>
}