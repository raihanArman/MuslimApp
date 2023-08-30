package com.raydev.anabstract.util

import com.raydev.anabstract.state.ResponseState

/**
 * @author Raihan Arman
 * @date 13/08/23
 */
val <T>ResponseState<T>.value: T?
    get() {
        return if (this is ResponseState.Success) {
            this.data
        } else {
            null
        }
    }
