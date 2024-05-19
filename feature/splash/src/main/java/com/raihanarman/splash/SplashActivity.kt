package com.raihanarman.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.rctiplus.main.MainActivity
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author Raihan Arman
 * @date 25/09/23
 */
class SplashActivity : AppCompatActivity() {
    private val viewModel: SplashViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        // Handle the splash screen transition.
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        // Keep the splash screen visible for this Activity
        splashScreen.setKeepOnScreenCondition { true }
        setupObserve()
    }

    private fun setupObserve() {
        lifecycleScope.launch {
            viewModel.observeEvent.collect {
                when (it) {
                    SplashEvent.Initial -> {}
                    SplashEvent.OnNavigateToMain -> {
                        MainActivity.goToMain(this@SplashActivity, true)
                    }
                }
            }
        }
    }
}
