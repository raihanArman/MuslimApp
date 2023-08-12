package com.raydev.shared.di

import com.raydev.shared.LocationManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * @author Raihan Arman
 * @date 12/08/23
 */
object SharedModule {
    val sharedModule = module {
        single { LocationManager(androidContext()) }
    }
}