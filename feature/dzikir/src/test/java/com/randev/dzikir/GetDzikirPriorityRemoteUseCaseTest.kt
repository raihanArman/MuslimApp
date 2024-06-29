package com.randev.dzikir

import app.cash.turbine.test
import com.randev.dzikir.api.client.GetDzikirPriorityHttpClient
import com.randev.dzikir.api.model.DzikirPriorityModel
import com.randev.dzikir.api.usecase.GetDzikirPriorityRemoteUseCase
import com.randev.dzikir.domain.model.DzikirPriority
import com.raydev.anabstract.exception.Connectivity
import com.raydev.anabstract.exception.ConnectivityException
import com.raydev.anabstract.exception.Unexpected
import com.raydev.anabstract.exception.UnexpectedException
import com.raydev.anabstract.state.FirestoreClientResult
import com.raydev.anabstract.state.FirestoreDomainResult
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

/**
 * @author Raihan Arman
 * @date 18/06/24
 */
class GetDzikirPriorityRemoteUseCaseTest {
    private val client: GetDzikirPriorityHttpClient = mockk()
    private lateinit var sut: GetDzikirPriorityRemoteUseCase

    @Before
    fun setUp() {
        sut = GetDzikirPriorityRemoteUseCase(client)
    }

    @Test
    fun testInitDoesNotLoad() {
        verify(exactly = 0) {
            client.getDzikirPriority()
        }
    }

    @Test
    fun testLoadRequestData() = runBlocking {
        every {
            client.getDzikirPriority()
        } returns flowOf()

        sut.load().test {
            awaitComplete()
        }

        verify(exactly = 1) {
            client.getDzikirPriority()
        }

        confirmVerified(client)
    }

    @Test
    fun testLoadRequestDataTwice() = runBlocking {
        every {
            client.getDzikirPriority()
        } returns flowOf()

        sut.load().test {
            awaitComplete()
        }

        sut.load().test {
            awaitComplete()
        }

        verify(exactly = 2) {
            client.getDzikirPriority()
        }

        confirmVerified(client)
    }

    @Test
    fun testDeliversConnectivityErrorOnClientError() {
        expect(
            sut = sut,
            receivedResult = FirestoreClientResult.Failure(ConnectivityException()),
            expectedResult = Connectivity(),
            exactly = 1
        )
    }

    @Test
    fun testDeliversUnexpectedErrorOnClientError() {
        expect(
            sut = sut,
            receivedResult = FirestoreClientResult.Failure(UnexpectedException()),
            expectedResult = Unexpected(),
            exactly = 1
        )
    }

    @Test
    fun testLoadDeliversSuccess() {
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

        val domainModel = listOf(
            DzikirPriority(
                id = "1",
                content = "test",
                translate = "test"
            ),
            DzikirPriority(
                id = "1",
                content = "test",
                translate = "test"
            )
        )

        expect(
            sut = sut,
            receivedResult = FirestoreClientResult.Success(models),
            expectedResult = domainModel,
            exactly = 1
        )
    }

    @Test
    fun testLoadDeliversSuccessWithEmptyData() {
        expect(
            sut = sut,
            receivedResult = FirestoreClientResult.Success(emptyList()),
            expectedResult = emptyList<List<DzikirPriority>>(),
            exactly = 1
        )
    }

    private fun expect(
        sut: GetDzikirPriorityRemoteUseCase,
        receivedResult: FirestoreClientResult<List<DzikirPriorityModel>>,
        expectedResult: Any,
        exactly: Int = -1
    ) = runBlocking {
        every {
            client.getDzikirPriority()
        } returns flowOf(receivedResult)

        sut.load().test {
            when (val received = awaitItem()) {
                is FirestoreDomainResult.Failure -> {
                    assertEquals(expectedResult::class.java, received.exception::class.java)
                }
                is FirestoreDomainResult.Success -> {
                    assertEquals(expectedResult, received.data)
                }
            }

            awaitComplete()
        }

        verify(exactly = exactly) {
            client.getDzikirPriority()
        }

        confirmVerified(client)
    }
}
