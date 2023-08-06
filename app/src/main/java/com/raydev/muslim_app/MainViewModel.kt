package com.raydev.muslim_app

import com.raydev.anabstract.base.BaseViewModel
import com.raydev.navigation.AppNavigator

/**
 * @author Raihan Arman
 * @date 06/08/23
 */
class MainViewModel(
    private val appNavigator: AppNavigator
): BaseViewModel() {
    val navigationChannel = appNavigator.navigationChannel
}