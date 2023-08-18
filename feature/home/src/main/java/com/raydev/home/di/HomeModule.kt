package com.raydev.home.di

import com.raydev.home.ui.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object HomeModule {
    val homeModule = module {
        viewModel {
            HomeViewModel(get())
        }
    }
}