package com.raihanarman.here_api

import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author Raihan Arman
 * @date 18/09/23
 */

fun hereNetworkModule(apiKey: String) = module {
    single(named("here_okhttp")) {
        val okhttp = get<OkHttpClient>().newBuilder().addInterceptor { chain ->
            val originalRequest = chain.request()
            val url = originalRequest.url.newBuilder()
                .addQueryParameter("apiKey", apiKey)
                .build()

            val newRequest = originalRequest.newBuilder().url(url).build()
            chain.proceed(newRequest)
        }
        okhttp.build()
    }

    single(named("here_network")) {
        Retrofit.Builder()
            .baseUrl("https://discover.search.hereapi.com/")
            .addConverterFactory(GsonConverterFactory.create(get()))
            .client(get(named("here_okhttp")))
            .build()
    }
}
