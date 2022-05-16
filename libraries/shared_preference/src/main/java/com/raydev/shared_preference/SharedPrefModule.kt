package com.raydev.shared_preference

import android.content.Context
import android.content.SharedPreferences
import com.raydev.shared.util.KeyShared
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object SharedPrefModule {
    val sharedPrefModule = module{
        single {
            androidContext().getSharedPreferences(KeyShared.PREF_KEYS, Context.MODE_PRIVATE)
        }
    }
}