package com.raydev.anabstract.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

/**
 * @author Raihan Arman
 * @date 06/08/23
 */
abstract class BaseViewModel : ViewModel(), CoroutineScope {
    private val parentJob = SupervisorJob()
    var isInitialized = MutableLiveData(false)
    override val coroutineContext: CoroutineContext = parentJob + Dispatchers.Main

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}
