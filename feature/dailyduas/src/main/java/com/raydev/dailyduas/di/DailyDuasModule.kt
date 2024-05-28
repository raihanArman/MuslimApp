package com.raydev.dailyduas.di

import com.raydev.dailyduas.api.dailyDuasApiModule
import com.raydev.dailyduas.api_infra.dailyDuasApiInfraModule
import com.raydev.dailyduas.presentation.dailyDuasPresentationModule

/**
 * @author Raihan Arman
 * @date 22/05/24
 */
val dailyDuasModule = listOf(
    dailyDuasApiInfraModule,
    dailyDuasApiModule,
    dailyDuasPresentationModule
)
