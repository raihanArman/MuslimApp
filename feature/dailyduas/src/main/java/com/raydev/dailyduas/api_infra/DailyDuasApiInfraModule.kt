package com.raydev.dailyduas.api_infra

import com.raydev.dailyduas.api.FirestoreClient
import org.koin.dsl.module

/**
 * @author Raihan Arman
 * @date 22/05/24
 */
val dailyDuasApiInfraModule = module {
    single<DailyDuasFirestoreService> {
        DailyDuasFirestoreServiceImpl()
    }

    single<FirestoreClient> {
        DailyDuasFirestoreClient(get())
    }
}
