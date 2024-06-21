package com.randev.dzikir

import app.cash.turbine.test
import com.randev.dzikir.api.DzikirPriorityModel
import com.randev.dzikir.api_infra.DzikirFirestoreService
import com.raydev.anabstract.exception.ConnectivityException
import com.raydev.anabstract.exception.UnexpectedException
import com.raydev.anabstract.state.FirestoreClientResult
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
 * @date 21/06/24
 */
class GetDzikirPriorityFirestoreClient(
    private val service: DzikirFirestoreService
) {
    fun getDzikirPriority(): Flow<FirestoreClientResult<List<DzikirPriorityModel>>> = flow {
        try {
            service.getDzikirPriority()
        } catch (e: Exception) {
            when (e) {
                is IOException -> {
                    emit(FirestoreClientResult.Failure(ConnectivityException()))
                }
                else -> {
                    emit(FirestoreClientResult.Failure(UnexpectedException()))
                }
            }
        }
    }
}

class GetDzikirPriorityFirestoreClientTest {
    private val service: DzikirFirestoreService = mockk()
    private lateinit var sut: GetDzikirPriorityFirestoreClient

    @Before
    fun setUp() {
        sut = GetDzikirPriorityFirestoreClient(service)
    }

    @Test
    fun testGetFailsOnConnectivity() {
        expect(
            sut = sut,
            expectedResult = ConnectivityException()
        )
    }

    @Test
    fun testGetFailsOnUnexpected() {
        expect(
            sut = sut,
            expectedResult = UnexpectedException()
        )
    }

    private fun expect(
        sut: GetDzikirPriorityFirestoreClient,
        receivedResult: Any? = null,
        expectedResult: Any
    ) = runBlocking {
        when {
            expectedResult is ConnectivityException -> {
                coEvery {
                    service.getDzikirPriority()
                } throws IOException()
            }

            else -> {
                coEvery {
                    service.getDzikirPriority()
                } throws Exception()
            }
        }

        sut.getDzikirPriority().test {
            when (val received = awaitItem()) {
                is FirestoreClientResult.Failure -> {
                    assertEquals(expectedResult::class.java, received.exception::class.java)
                }
                is FirestoreClientResult.Success -> {}
            }

            awaitComplete()
        }

        coVerify {
            service.getDzikirPriority()
        }

        confirmVerified(service)
    }
}
