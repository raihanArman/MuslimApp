package com.raydev.muslim_app.deeplink

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.deeplinkdispatch.DeepLinkHandler
import com.raydev.quran.deeplink.QuranDeepLink
import com.raydev.quran.deeplink.QuranDeepLinkLoader

@DeepLinkHandler(
    AppDeepLinkModule::class,
    QuranDeepLink::class,
)
class DeepLinkRouterActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val deepLinkDelegate = DeepLinkDelegate(
            AppDeepLinkModuleLoader(),
            QuranDeepLinkLoader()

        )
        deepLinkDelegate.dispatchFrom(this)
        finish()
    }
}