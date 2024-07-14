package com.raydev.muslim_app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.raihanarman.bookmark.di.bookmarkModule
import com.raihanarman.here_api.hereNetworkModule
import com.raihanarman.location.di.locationModule
import com.raihanarman.read_quran.di.readQuranModule
import com.raihanarman.splash.splashModule
import com.randev.dzikir.di.dzikirModule
import com.raydev.dailyduas.di.dailyDuasModule
import com.raydev.data.di.DataSourceModule.localDataSourceModule
import com.raydev.data.di.DataSourceModule.remoteDataSourceModule
import com.raydev.data.di.DataSourceModule.sharedPreferenceSourceModule
import com.raydev.data.di.apiModule
import com.raydev.data.di.databaseModule
import com.raydev.data.di.repositoryModule
import com.raydev.domain.di.useCaseModule
import com.raydev.home.di.homeModule
import com.raydev.navigation.NavigationModule.navigatorModule
import com.raydev.network.di.networkModule
import com.raydev.prayer.di.prayerModule
import com.raydev.quran.di.quranModule
import com.raydev.shared_preference.sharedPrefModule
import com.raydev.shortvideo.di.shortVideoApiModule
import com.raydev.workmanager.di.workerModule
import com.rctiplus.main.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MuslimApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MuslimApplication)
            modules(
                listOf(
                    networkModule,
                    databaseModule,
                    workerModule,
                    sharedPrefModule,
                    locationModule,
                    hereNetworkModule(BuildConfig.HERE_API_KEY),
                    apiModule,
                    remoteDataSourceModule,
                    localDataSourceModule,
                    sharedPreferenceSourceModule,
                    repositoryModule,
                    useCaseModule,
                    navigatorModule,
                    prayerModule,
                    mainModule,
                    splashModule,
                    homeModule,
                    quranModule,
                    readQuranModule,
                    bookmarkModule,
                ) + dailyDuasModule + dzikirModule + shortVideoApiModule
            )
        }
    }
}
