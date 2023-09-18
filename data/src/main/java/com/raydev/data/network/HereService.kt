package com.raydev.data.network

import com.raydev.data.model.response.HereDiscoverResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Raihan Arman
 * @date 18/09/23
 */
interface HereService {
    @GET("v1/discover")
    suspend fun searchMosque(
        @Query("at") coordinate: String,
        @Query("limit") limit: Int,
        @Query("q") query: String = "mosque"
    ): HereDiscoverResponse
}
