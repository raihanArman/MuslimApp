package com.raydev.dailyduas

import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.io.IOException

/**
 * @author Raihan Arman
 * @date 20/05/24
 */

sealed class FirestoreClientResult {
    data class Success(val root: List<DailyDuasResponse>) : FirestoreClientResult()
    data class Failure(val exception: Exception) : FirestoreClientResult()
}

class DailyDuasFirestoreClient(
    private val service: DailyDuasFirestoreService
) {
    fun getDailyDuas(): Flow<FirestoreClientResult> {
        return flow {
            try {
                service.getDailyDuas()
            } catch (e: Exception) {
                emit(FirestoreClientResult.Failure(ConnectivityException()))
            }
        }
    }
}
class DailyDuasFirestoreClientTest {
    private val service = mockk<DailyDuasFirestoreService>()
    private lateinit var sut: DailyDuasFirestoreClient

    @Before
    fun setUp() {
        sut = DailyDuasFirestoreClient(service)
    }

    @Test
    fun testGetFailsOnConnectivity() = runBlocking {
        coEvery {
            service.getDailyDuas()
        } throws IOException()

        sut.getDailyDuas().test {
            when (val received = awaitItem()) {
                is FirestoreClientResult.Failure -> {
                    assertEquals(ConnectivityException()::class.java, received.exception::class.java)
                }
                else -> {}
            }

            awaitComplete()
        }

        coVerify {
            service.getDailyDuas()
        }

        confirmVerified(service)
    }
}
