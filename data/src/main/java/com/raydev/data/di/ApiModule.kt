package com.raydev.data.di

import com.raydev.data.network.PrayerService
import com.raydev.data.network.QuranService
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

object ApiModule {
    val apiModule = module {
        single {
            get<Retrofit>(named("quran")).create(QuranService::class.java)
        }

        single {
            get<Retrofit>(named("prayer")).create(PrayerService::class.java)
        }
    }
}