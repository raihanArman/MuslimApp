package com.raydev.domain.di

import com.raydev.domain.usecase.QuranUseCase
import org.koin.dsl.module

object UseCaseModule {
    var useCaseModule = module {
        factory {
            QuranUseCase(get())
        }
    }
}