package com.raihanarman.splash

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * @author Raihan Arman
 * @date 06/08/23
 */
val splashModule = module {
    viewModel { SplashViewModel(get(), get(), get()) }
}