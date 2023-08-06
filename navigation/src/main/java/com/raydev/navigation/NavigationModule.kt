package com.raydev.navigation

import org.koin.dsl.module

/**
 * @author Raihan Arman
 * @date 06/08/23
 */
object NavigationModule {
    val navigatorModule = module {
        single<AppNavigator> { AppNavigatorImpl() }
    }
}