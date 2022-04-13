package com.raydev.anabstract.base

interface BaseView {
    fun onMessage(message: String?)
    fun onMessage(stringResId: Int)
    fun isNetworkConnect(): Boolean
}