package com.randev.dzikir.di

import com.randev.dzikir.api.client.GetDzikirHttpClient
import com.randev.dzikir.api.client.GetDzikirPriorityHttpClient
import com.randev.dzikir.api.usecase.GetDzikirPriorityRemoteUseCase
import com.randev.dzikir.api.usecase.GetDzikirRemoteUseCase
import com.randev.dzikir.api_infra.client.GetDzikirFirestoreClient
import com.randev.dzikir.api_infra.client.GetDzikirPriorityFirestoreClient
import com.randev.dzikir.api_infra.service.DzikirFirestoreService
import com.randev.dzikir.api_infra.service.DzikirFirestoreServiceImpl
import com.randev.dzikir.domain.usecase.GetDzikirPriorityUseCase
import com.randev.dzikir.domain.usecase.GetDzikirUseCase
import com.randev.dzikir.presentation.dzikir.viewmodel.DzikirViewModel
import com.randev.dzikir.presentation.dzikir_priority.viewmodel.DzikirPriorityViewModel
import org.koin.dsl.module

/**
 * @author Raihan Arman
 * @date 30/06/24
 */
internal val dzikirApiInfraModule = module {
    single<DzikirFirestoreService> {
        DzikirFirestoreServiceImpl()
    }

    single<GetDzikirPriorityHttpClient> {
        GetDzikirPriorityFirestoreClient(get())
    }

    single<GetDzikirHttpClient> {
        GetDzikirFirestoreClient(get())
    }
}

internal val dzikirApiModule = module {
    single<GetDzikirPriorityUseCase> {
        GetDzikirPriorityRemoteUseCase(get())
    }
    single<GetDzikirUseCase> {
        GetDzikirRemoteUseCase(get())
    }
}

internal val dzikirPresentationModule = module {
    single {
        DzikirPriorityViewModel(get())
    }
    single {
        DzikirViewModel(get())
    }
}

val dzikirModule = listOf(
    dzikirApiInfraModule,
    dzikirApiModule,
    dzikirPresentationModule
)
