package com.raydev.data.di

import com.raydev.data.network.QuranService
import org.koin.dsl.module
import retrofit2.Retrofit

object ApiModule {
    val apiModule = module {
        single {
            get<Retrofit>().create(QuranService::class.java)
        }
    }
}