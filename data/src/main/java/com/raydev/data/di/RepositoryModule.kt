package com.raydev.data.di

import com.raydev.data.repository.PrayerRepositoryImpl
import com.raydev.data.repository.QuranRepositoryImpl
import com.raydev.domain.repository.PrayerRepository
import com.raydev.domain.repository.QuranRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object RepositoryModule {
    var repositortModule = module {

        factory<QuranRepository> {
            QuranRepositoryImpl(
                get(),get(), get(), get(), androidContext()
            )
        }

        single<PrayerRepository>{
            PrayerRepositoryImpl(
                get(),
                get()
            )
        }

    }
}