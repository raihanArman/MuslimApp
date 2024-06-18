package com.raydev.dailyduas.di

import com.raydev.dailyduas.api.GetDailyDuasFirestoreClient
import com.raydev.dailyduas.api.GetDailyDuasFirestoreUseCase
import com.raydev.dailyduas.api_infra.DailyDuasFirestoreClient
import com.raydev.dailyduas.api_infra.DailyDuasFirestoreService
import com.raydev.dailyduas.api_infra.DailyDuasFirestoreServiceImpl
import com.raydev.dailyduas.domain.GetDuasUseCase
import com.raydev.dailyduas.presentation.dailyDuasPresentationModule
import org.koin.dsl.module

/**
 * @author Raihan Arman
 * @date 22/05/24
 */
val dailyDuasApiInfraModule = module {
    single<DailyDuasFirestoreService> {
        DailyDuasFirestoreServiceImpl()
    }

    single<GetDailyDuasFirestoreClient> {
        DailyDuasFirestoreClient(get())
    }
}

val dailyDuasApiModule = module {
    single<GetDuasUseCase> {
        GetDailyDuasFirestoreUseCase(get())
    }
}

val dailyDuasModule = listOf(
    dailyDuasApiInfraModule,
    dailyDuasApiModule,
    dailyDuasPresentationModule
)
