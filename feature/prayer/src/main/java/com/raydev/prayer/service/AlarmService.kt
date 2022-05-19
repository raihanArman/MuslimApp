package com.raydev.prayer.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.os.VibrationEffect
import android.os.Vibrator
import com.raydev.prayer.R


class AlarmService : Service() {
    internal lateinit var player: MediaPlayer
    var pattern = longArrayOf(0, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000)
    var mAmplitudes = intArrayOf(0, 255, 0, 255, 0, 255, 0, 255, 0, 255, 0)


    override fun onCreate() {
        super.onCreate()
//        val uriSound: Uri =
//            Uri.parse("android.resource://" + this.packageName.toString() + "/" + R.raw.adzan_fajr)

        player = MediaPlayer.create(this, R.raw.adzan_fajr)
//        player.isLooping = true // Set looping
        player.setVolume(100f, 100f)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createWaveform(pattern, mAmplitudes, -1))
        } else {
            vibrator.vibrate(pattern, -1)
        }
        player.start()
        player.setOnCompletionListener {
            vibrator.cancel()
            player.stop()
            this.stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        player.stop()
        player.release()
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
}