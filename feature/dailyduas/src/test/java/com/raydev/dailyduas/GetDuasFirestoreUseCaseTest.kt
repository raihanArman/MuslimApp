package com.raydev.dailyduas

import app.cash.turbine.test
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

/**
 * @author Raihan Arman
 * @date 20/05/24
 */
sealed class FirestoreResult {
    data class Success(val root: List<DailyDuas>) : FirestoreResult()
    data class Failure(val exception: Exception) : FirestoreResult()
}

interface FirestoreClient {
    fun getDailyDuas(): Flow<FirestoreClientResult>
}

class GetDailyDuasFirestoreUseCase(
    private val client: FirestoreClient
) {
    fun getDailyDuas(): Flow<FirestoreResult> {
        return flow {
            client.getDailyDuas().collect { result ->
                when (result) {
                    is FirestoreClientResult.Failure -> {
                        when (result.exception) {
                            is ConnectivityException -> {
                                emit(FirestoreResult.Failure(Connectivity()))
                            }
                        }
                    }
                    else -> {}
                }
            }
        }
    }
}

class GetDailyDuasFirestoreUseCaseTest {
    private val client = mockk<FirestoreClient>()
    private lateinit var sut: GetDailyDuasFirestoreUseCase

    @Before
    fun setUp() {
        sut = GetDailyDuasFirestoreUseCase(client)
    }

    @Test
    fun testInitDoesNotRequestData() {
        verify(exactly = 0) {
            client.getDailyDuas()
        }

        confirmVerified(client)
    }

    @Test
    fun testLoadRequestData() = runBlocking {
        every {
            client.getDailyDuas()
        } returns flowOf()

        sut.getDailyDuas().test {
            awaitComplete()
        }

        verify(exactly = 1) {
            client.getDailyDuas()
        }

        confirmVerified(client)
    }

    @Test
    fun testLoadTwiceRequestData() = runBlocking {
        every {
            client.getDailyDuas()
        } returns flowOf()

        sut.getDailyDuas().test {
            awaitComplete()
        }

        sut.getDailyDuas().test {
            awaitComplete()
        }

        verify(exactly = 2) {
            client.getDailyDuas()
        }

        confirmVerified(client)
    }

    @Test
    fun testLoadDeliversConnectivityError() = runBlocking {
        every {
            client.getDailyDuas()
        } returns flowOf(FirestoreClientResult.Failure(ConnectivityException()))

        sut.getDailyDuas().test {
            when (val received = awaitItem()) {
                is FirestoreResult.Failure -> {
                    assertEquals(Connectivity()::class.java, received.exception::class.java)
                }
                else -> {}
            }

            awaitComplete()
        }

        verify(exactly = 1) {
            client.getDailyDuas()
        }

        confirmVerified(client)
    }
}
