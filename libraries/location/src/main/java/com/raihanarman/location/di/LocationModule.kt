package com.raihanarman.location.di

import com.raihanarman.location.LocationManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * @author Raihan Arman
 * @date 12/08/23
 */
val locationModule = module {
    single { LocationManager(androidContext()) }
}