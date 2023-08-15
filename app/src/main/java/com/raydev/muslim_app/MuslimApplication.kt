package com.raydev.muslim_app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.raihanarman.read_quran.di.ReadQuranModule.readQuranModule
import com.raihanarman.splash.SplashModule.splashModule
import com.raydev.data.di.ApiModule.apiModule
import com.raydev.data.di.DataSourceModule.localDataSourceModule
import com.raydev.data.di.DataSourceModule.remoteDataSourceModule
import com.raydev.data.di.DataSourceModule.sharedPreferenceSourceModule
import com.raydev.data.di.DatabaseModule.databaseModule
import com.raydev.data.di.RepositoryModule.repositortModule
import com.raydev.domain.di.UseCaseModule.useCaseModule
import com.raydev.home.di.HomeModule.homeModule
import com.raydev.muslim_app.MainModule.mainModule
import com.raydev.navigation.NavigationModule.navigatorModule
import com.raydev.network.di.NetworkModule.networkModule
import com.raydev.quran.di.QuranModule.quranModule
import com.raihanarman.location.di.locationModule
import com.raydev.shared_preference.sharedPrefModule
import com.raydev.workmanager.di.WorkerModule.workerModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MuslimApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MuslimApplication)
            modules(listOf(
                networkModule,
                databaseModule,
                workerModule,
                sharedPrefModule,
                locationModule,
                apiModule,
                remoteDataSourceModule,
                localDataSourceModule,
                sharedPreferenceSourceModule,
                repositortModule,
                useCaseModule,
                navigatorModule,
                mainModule,
                splashModule,
                homeModule,
                quranModule,
                readQuranModule
            ))
        }
    }
}