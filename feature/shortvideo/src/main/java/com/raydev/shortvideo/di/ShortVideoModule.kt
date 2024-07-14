package com.raydev.shortvideo.di

import com.raydev.shortvideo.api.GetShortVideoClient
import com.raydev.shortvideo.api.GetShortVideoRemoteUseCase
import com.raydev.shortvideo.api_infra.GetShortVideoFirestoreClient
import com.raydev.shortvideo.api_infra.ShortVideoService
import com.raydev.shortvideo.api_infra.ShortVideoServiceImpl
import com.raydev.shortvideo.domain.GetShortVideoUseCase
import com.raydev.shortvideo.presentation.GetShortVideoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * @author Raihan Arman
 * @date 14/07/24
 */
private val shortVideoApiInfraModule = module {
    single<ShortVideoService> {
        ShortVideoServiceImpl()
    }

    single<GetShortVideoClient> {
        GetShortVideoFirestoreClient(get())
    }
}

private val shortVideoApiModule = module {
    single<GetShortVideoUseCase> {
        GetShortVideoRemoteUseCase(get())
    }
}

private val shortVideoPresentationModule = module {
    viewModel {
        GetShortVideoViewModel(get())
    }
}

val shortVideoModule = listOf(
    shortVideoApiInfraModule,
    shortVideoApiModule,
    shortVideoPresentationModule
)
