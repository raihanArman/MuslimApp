package com.raydev.data.di

import com.raydev.data.datasource.local.QuranLocalDataSource
import com.raydev.data.datasource.remote.QuranRemoteDataSource
import org.koin.dsl.module

object DataSourceModule {
    var remoteDataSourceModule = module {
        factory {
            QuranRemoteDataSource(get())
        }
    }

    var localDataSourceModule = module {
        factory {
            QuranLocalDataSource(get())
        }
    }
}