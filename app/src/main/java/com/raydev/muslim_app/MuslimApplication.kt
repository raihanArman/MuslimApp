package com.raydev.muslim_app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.raydev.cache.CacheModule.cacheModule
import com.raydev.data.di.ApiModule.apiModule
import com.raydev.data.di.RemoteDataSourceModule.remoteDataSourceModule
import com.raydev.data.di.RepositoryModule.repositortModule
import com.raydev.domain.di.UseCaseModule.useCaseModule
import com.raydev.network.di.NetworkModule.networkModule
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.parameter.parametersOf
import retrofit2.Retrofit

class MuslimApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MuslimApplication)
            modules(listOf(
                networkModule,
                cacheModule,
                apiModule,
                remoteDataSourceModule,
                repositortModule,
                useCaseModule
            ))
        }
    }
}