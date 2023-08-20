package com.raydev.home.utils

import android.os.CountDownTimer

/**
 * @author Raihan Arman
 * @date 20/08/23
 */
fun tick(millisInFuture:Long, interval:Long,onTick:()->Unit): CountDownTimer {
    return object: CountDownTimer(millisInFuture,interval){
        override fun onTick(millisUntilFinished: Long) {
            onTick.invoke()
        }
        override fun onFinish() {
        }
    }
}