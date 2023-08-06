package com.raydev.data.di

import com.raydev.data.datasource.local.AyatLineLocalDataSource
import com.raydev.data.datasource.local.AyatLocalDataSource
import com.raydev.data.datasource.local.SurahLocalDataSource
import com.raydev.data.datasource.pref.SharedPreferenceSource
import com.raydev.data.datasource.remote.PrayerRemoteDataSource
import com.raydev.data.datasource.remote.QuranRemoteDataSource
import org.koin.dsl.module

object DataSourceModule {
    var remoteDataSourceModule = module {
        factory {
            QuranRemoteDataSource(get())
        }

        factory {
            PrayerRemoteDataSource(get())
        }
    }

    var localDataSourceModule = module {
        factory {
            AyatLocalDataSource(get())
        }
        factory {
            AyatLineLocalDataSource(get())
        }
        factory {
            SurahLocalDataSource(get())
        }
    }

    val sharedPreferenceSourceModule = module {
        factory {
            SharedPreferenceSource(get())
        }
    }
}