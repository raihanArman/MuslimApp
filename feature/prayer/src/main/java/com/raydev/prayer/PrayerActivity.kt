package com.raydev.prayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.airbnb.deeplinkdispatch.DeepLink
import com.raydev.shared.deeplink.AppLink


@DeepLink(AppLink.PrayerFeature.PRAYER_LINK)
class PrayerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prayer)
    }
}