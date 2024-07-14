package com.rctiplus.main

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.util.Date
import kotlin.system.measureTimeMillis

/**
 * @author Raihan Arman
 * @date 19/11/23
 */
class CobaTest {
    suspend fun getBar(): Int {
        delay(1000)
        return 10
    }

    suspend fun getFoo(): Int {
        delay(1000)
        return 20
    }

    @Test
    fun testSequential() {
        runBlocking {
            val time = measureTimeMillis {
                getFoo()
                getBar()
            }

            println("Time ampas kuda -> $time")
        }
    }

    @Test
    fun testAsyncronous() {
        runBlocking {
            val time = measureTimeMillis {
                val job1 = GlobalScope.launch { getFoo() }
                val job2 = GlobalScope.launch { getBar() }

                joinAll(job1, job2)
            }

            println("Time ampas kuda -> $time")
        }
    }

    @Test
    fun testFlow() = runBlocking {
        checkFlow().collect {
            println("Ampas kuda -> $it")
        }

        checkFlow().collect {
            println("AMPAS KUDA -> $it")
        }
    }

    fun checkFlow(): Flow<Int> = flow {
        for (i in 1..7) {
            delay(1000)
            emit(i)
        }
    }

    @Test
    fun testStateFlow() {
        val stateFlow = MutableSharedFlow<Int>()
        val scope = CoroutineScope(Dispatchers.IO)

        scope.launch {
            repeat(10) {
                println("   SEND    : $it : ${Date()}")
                stateFlow.emit(it)
                delay(1000)
            }
        }

        scope.launch {
            stateFlow
                .map {
                    "Receive Job : $it : ${Date()}"
                }
                .collect {
                    delay(5000)
                    println(it)
                }
        }

        runBlocking {
            delay(22_000)
            scope.cancel()
        }
    }
}
