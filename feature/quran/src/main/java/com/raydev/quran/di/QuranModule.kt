package com.raydev.quran.di

import com.raydev.quran.viewmodel.AyatViewModel
import com.raydev.quran.viewmodel.SurahViewModel
import org.koin.android.viewmodel.compat.ScopeCompat.viewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

object QuranModule {
    val quranModule = module{
        viewModel {
            SurahViewModel(get())
        }

        viewModel {
            AyatViewModel(get())
        }
    }
}