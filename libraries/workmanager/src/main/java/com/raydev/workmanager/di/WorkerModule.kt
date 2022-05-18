package com.raydev.workmanager.di

import androidx.work.WorkManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object WorkerModule {
    var workerModule = module {
        single {
            WorkManager.getInstance(androidContext())
        }
    }
}