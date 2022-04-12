package com.raydev.domain.di

import com.raydev.domain.usecase.quran.GetAyatUseCase
import com.raydev.domain.usecase.quran.GetSurahUseCase
import org.koin.dsl.module

object UseCaseModule {
    var useCaseModule = module {
        single {
            GetAyatUseCase(get())
        }
        single {
            GetSurahUseCase(get())
        }
    }
}