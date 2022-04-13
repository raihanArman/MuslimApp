package com.raydev.domain.util

import com.raydev.anabstract.state.ResponseState
import kotlinx.coroutines.flow.Flow

interface UseCase<Type, Params> {
    fun call(params: Params): Flow<ResponseState<Type>>
}

class NoParams{

}