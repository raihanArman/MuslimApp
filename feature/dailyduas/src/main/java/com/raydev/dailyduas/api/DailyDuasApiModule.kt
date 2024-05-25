package com.raydev.dailyduas.api

import com.raydev.dailyduas.domain.GetDuasUseCase
import org.koin.dsl.module

/**
 * @author Raihan Arman
 * @date 22/05/24
 */
val dailyDuasApiModule = module {
    single<GetDuasUseCase> {
        GetDailyDuasFirestoreUseCase(get())
    }
}
