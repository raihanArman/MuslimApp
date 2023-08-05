package com.raydev.quran.di

import com.raydev.quran.viewmodel.AyatViewModel
import com.raydev.quran.viewmodel.SurahViewModel
import com.raydev.quran.work.FileDownloadHelper
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object QuranModule {
    val quranModule = module{
        viewModel {
            SurahViewModel()
        }

        viewModel {
            AyatViewModel()
        }

        single {
            FileDownloadHelper(get())
        }
    }
}