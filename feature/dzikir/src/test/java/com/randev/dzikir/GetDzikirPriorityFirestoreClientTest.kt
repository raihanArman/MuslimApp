package com.randev.dzikir

import app.cash.turbine.test
import com.randev.dzikir.api.DzikirPriorityModel
import com.randev.dzikir.api_infra.DzikirFirestoreService
import com.randev.dzikir.api_infra.DzikirPriorityResponse
import com.randev.dzikir.api_infra.GetDzikirPriorityFirestoreClient
import com.raydev.anabstract.exception.ConnectivityException
import com.raydev.anabstract.exception.UnexpectedException
import com.raydev.anabstract.state.FirestoreClientResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.io.IOException

/**
 * @author Raihan Arman
 * @date 21/06/24
 */
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

    @Test
    fun testGetSuccessResponse() {
        val responses = listOf(
            DzikirPriorityResponse(
                id = "1",
                content = "test",
                translate = "test"
            ),
            DzikirPriorityResponse(
                id = "1",
                content = "test",
                translate = "test"
            )
        )

        val models = listOf(
            DzikirPriorityModel(
                id = "1",
                content = "test",
                translate = "test"
            ),
            DzikirPriorityModel(
                id = "1",
                content = "test",
                translate = "test"
            )
        )

        expect(
            sut = sut,
            receivedResult = responses,
            expectedResult = FirestoreClientResult.Success(models)
        )
    }

    @Test
    fun testGetsSuccessWithEmptyResponse() {
        expect(
            sut = sut,
            receivedResult = emptyList<DzikirPriorityResponse>(),
            expectedResult = FirestoreClientResult.Success<List<DzikirPriorityModel>>(emptyList())
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

            expectedResult is FirestoreClientResult.Success<*> -> {
                coEvery {
                    service.getDzikirPriority()
                } returns receivedResult as List<DzikirPriorityResponse>
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
                is FirestoreClientResult.Success -> {
                    assertEquals(expectedResult, received)
                }
            }

            awaitComplete()
        }

        coVerify {
            service.getDzikirPriority()
        }

        confirmVerified(service)
    }
}
