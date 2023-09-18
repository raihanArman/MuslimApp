package com.raydev.data.di

import com.raydev.data.network.HereService
import com.raydev.data.network.PrayerService
import com.raydev.data.network.QuranService
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    single {
        get<Retrofit>().create(QuranService::class.java)
    }

    single {
        get<Retrofit>().create(PrayerService::class.java)
    }

    single {
        get<Retrofit>(named("here_network")).create(HereService::class.java)
    }
}
