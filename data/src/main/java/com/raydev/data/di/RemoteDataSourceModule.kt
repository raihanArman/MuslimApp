package com.raydev.data.di

import com.raydev.data.datasource.remote.QuranRemoteDataSource
import org.koin.dsl.module

object RemoteDataSourceModule {
    var remoteDataSourceModule = module {
        single {
            QuranRemoteDataSource(get())
        }
    }
}