package com.raydev.muslim_app

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.raydev.shared.deeplink.AppLink

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("${AppLink.QuranFeature.QURAN_LINK}"))
        startActivity(intent)

    }
}