package com.raydev.workmanager.di

import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.raydev.workmanager.work.FileDownloadHelper
import com.raydev.workmanager.work.FileDownloadWorker
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object WorkerModule {
    var workerModule = module {
        single {
            WorkManager.getInstance(androidContext())
        }
        single {
            FileDownloadHelper(get())
        }
    }
}