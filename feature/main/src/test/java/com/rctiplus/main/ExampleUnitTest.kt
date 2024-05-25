package com.rctiplus.main

import junit.framework.Assert.assertEquals
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.isActive
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import org.junit.Test
import java.util.Date
import java.util.concurrent.Executors
import kotlin.coroutines.CoroutineContext
import kotlin.system.measureTimeMillis

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testCoroutine() {
        runBlocking {
            GlobalScope.launch {
                println("AMPAS KUDA1")
                delay(2000)
                println("AMPAS KUDA2")
            }

            delay(2000)
        }

        println("AMPAS KUDA LUMPING")
    }

    @Test
    fun testLazyJob() {
        runBlocking {
            val job = GlobalScope.launch(start = CoroutineStart.LAZY) {
                println("AMPAS KUDA1")
                delay(2000)
                println("AMPAS KUDA2")
            }

            job.join()
        }
    }

    @Test
    fun testJoinAll() {
        runBlocking {
            val job1 = GlobalScope.launch(start = CoroutineStart.LAZY) {
                println("AMPAS KUDA1 job1")
                delay(2000)
                println("AMPAS KUDA2 job1")
            }
            val job2 = GlobalScope.launch(start = CoroutineStart.LAZY) {
                println("AMPAS KUDA1 job2")
                delay(2000)
                println("AMPAS KUDA2 job2")
            }

            joinAll(job1, job2)
        }
    }

    @Test
    fun testJobCannotCancel() {
        runBlocking {
            val job1 = GlobalScope.launch {
                ensureActive()
                println("AMPAS KUDA1 job1")

                delay(2000)

                ensureActive()
                println("AMPAS KUDA2 job1")
            }
            job1.cancel()
        }
    }

    @Test
    fun testJobCancelFinally() {
        runBlocking {
            val job = GlobalScope.launch {
                try {
                    println("AMPAS KUDA1 job1")

                    delay(2000)

                    println("AMPAS KUDA2 job1")
                } finally {
                    println("AMPAS KUDA2 Selesai")
                }
            }

            job.cancelAndJoin()
        }
    }

    @Test
    fun testCancellable() {
        runBlocking {
            val job = GlobalScope.launch {
                if (!isActive) {
                    println("AMPASSSS KUDAAAA")
                    throw CancellationException()
                }

                println("Start Coroutine ${Date()}")

                ensureActive()
                println("isActive 1 -> $isActive")
//                Thread.sleep(2000)

                ensureActive()
                println("isActive 2 -> $isActive")
                println("End Coroutine ${Date()}")
            }

            job.cancel()
            delay(3000)
        }
    }

    @Test
    fun testCanNotCancel() {
        runBlocking {
            val job = GlobalScope.launch {
                println("Start Coroutine ${Date()}")
                Thread.sleep(2000)
                println("End Coroutine ${Date()}")
            }

            job.cancel()
            delay(3000)
        }
    }

    @Test
    fun testCancellableFinally() {
        runBlocking {
            val job = GlobalScope.launch {
                try {
                    println("Start coroutine ${Date()}")
                    delay(2000)
                    println("End coroutine ${Date()}")
                } finally {
                    println("Finish")
                }
            }

            job.cancelAndJoin()
        }
    }

    @Test
    fun testTimeout() {
        runBlocking {
            val job = GlobalScope.launch {
                println("Start Coroutine")
                withTimeoutOrNull(5_000) {
                    repeat(100) {
                        delay(1000)
                        println("$it ${Date()}")
                    }
                }
                println("Finish Coroutine")
            }

            job.join()
        }
    }

    suspend fun getFoo(): Int {
        delay(1000)
        return 10
    }

    suspend fun getBar(): Int {
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
            println("Total time -> $time")
        }
    }

    @Test
    fun testConcurrent() {
        runBlocking {
            val time = measureTimeMillis {
                val job1 = GlobalScope.launch { getFoo() }
                val job2 = GlobalScope.launch { getBar() }

                joinAll(job1, job2)
            }

            println("Total time -> $time")
        }
    }

    @Test
    fun asyncTest() {
        runBlocking {
            val time = measureTimeMillis {
                val job1 = GlobalScope.async { getFoo() } // Deferred<20>
                val job2 = GlobalScope.async { getBar() } // Deferred<10>

                val total = job1.await() + job2.await()
                println("Result : $total")
            }

            println("Total time -> $time")
        }
    }

    @Test
    fun awaitAllTest() {
        runBlocking {
            val time = measureTimeMillis {
                val job1 = GlobalScope.async { getFoo() } // Deferred<20>
                val job2 = GlobalScope.async { getBar() } // Deferred<10>

                val total = awaitAll(job1, job2).sum()
                println("Result : $total")
            }

            println("Total time -> $time")
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Test
    fun testCoroutineContextTest() {
        runBlocking {
            val job = GlobalScope.launch {
                val context: CoroutineContext = coroutineContext
                println(context)
                println(context[Job])
                println(context[CoroutineDispatcher])
            }

            job.join()
        }
    }

    @Test
    fun testDispatcher() {
        runBlocking {
            println("runBlocking ${Thread.currentThread().name}")
            val job1 = GlobalScope.launch(Dispatchers.Default) {
                println("Job 1 ${Thread.currentThread().name}")
            }
            val job2 = GlobalScope.launch(Dispatchers.IO) {
                println("Job 2 ${Thread.currentThread().name}")
            }

            joinAll(job1, job2)
        }
    }

    @Test
    fun testUnconfined() {
        runBlocking {
            GlobalScope.launch(Dispatchers.Unconfined) {
                println("Unconfined : ${Thread.currentThread().name}")
                delay(1000)
                println("Unconfined : ${Thread.currentThread().name}")
            }
            GlobalScope.launch {
                println("Confined : ${Thread.currentThread().name}")
                delay(1000)
                println("Confined : ${Thread.currentThread().name}")
            }

            delay(2000)
        }
    }

    @Test
    fun testWithContext() {
        val dispatcherClient = Executors.newFixedThreadPool(10).asCoroutineDispatcher()
        runBlocking {
            val job = GlobalScope.launch(Dispatchers.IO) {
                println("1 ${Thread.currentThread().name}")
                withContext(dispatcherClient) {
                    println("2 ${Thread.currentThread().name}")
                }
                println("3 ${Thread.currentThread().name}")
            }

            job.join()
        }
    }

    @Test
    fun testParentChild() {
        runBlocking {
            val job = GlobalScope.launch {
                launch {
                    delay(2000)
                    println("Child 1 Done")
                }

                launch {
                    delay(4000)
                    println("Child 2 Done")
                }

                delay(1000)
                println("Parent Done")
            }

            job.cancelChildren()
            job.join()
        }
    }

    suspend fun runJob(number: Int) {
        println("Start job $number in thread ${Thread.currentThread().name}")
//        yield()
        println("End job $number in thread ${Thread.currentThread().name}")
    }

    @Test
    fun testYieldFunction() {
        val dispatcher = Executors.newFixedThreadPool(10).asCoroutineDispatcher()
        val scope = CoroutineScope(dispatcher)

        runBlocking {
            scope.launch { runJob(1) }
            scope.launch { runJob(2) }

            delay(2000)
        }
    }

    @Test
    fun testAwaitCancellation() {
        runBlocking {
            val job = launch {
                try {
                    println("Start Job")
                    awaitCancellation()
                } finally {
                    println("Cancelled")
                }
            }

            delay(5000)
            job.cancelAndJoin()
        }
    }

    @Test
    fun testJob() {
        val dispatcher = Executors.newFixedThreadPool(10).asCoroutineDispatcher()
        val scope = CoroutineScope(dispatcher)

        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            println("Error dari exception coroutine")
        }

        runBlocking {
            scope.launch {
                supervisorScope {
                    launch(exceptionHandler) {
                        println("Job child")
                        throw IllegalArgumentException("Job 2 failed")
                    }
                }
            }
            delay(3000)
        }
    }

    @Test
    fun testChannel() {
        runBlocking {
            val channel = Channel<Int>()

            val job1 = launch {
                println("send data 1 to channel")
                channel.send(1)
                println("send data 2 to channel")
                channel.send(2)
            }

            val job2 = launch {
//                println("receive data ${channel.receive()}")
//                println("receive data ${channel.receive()}")
            }

            joinAll(job1, job2)
            channel.close()
        }
    }
}
