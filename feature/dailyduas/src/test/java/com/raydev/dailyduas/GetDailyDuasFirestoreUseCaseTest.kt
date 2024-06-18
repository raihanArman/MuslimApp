package com.raydev.dailyduas

import app.cash.turbine.test
import com.raydev.anabstract.exception.Connectivity
import com.raydev.anabstract.exception.ConnectivityException
import com.raydev.anabstract.exception.Unexpected
import com.raydev.anabstract.exception.UnexpectedException
import com.raydev.anabstract.state.FirestoreClientResult
import com.raydev.anabstract.state.FirestoreDomainResult
import com.raydev.dailyduas.api.DailyDuasModel
import com.raydev.dailyduas.api.GetDailyDuasFirestoreUseCase
import com.raydev.dailyduas.api_infra.DailyDuasFirestoreClient
import com.raydev.dailyduas.domain.DailyDuas
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
 * @date 20/05/24
 */

class GetDailyDuasFirestoreUseCaseTest {
    private val client = mockk<DailyDuasFirestoreClient>()
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
        expect(
            sut = sut,
            receivedResult = FirestoreClientResult.Failure(ConnectivityException()),
            expectedResult = FirestoreDomainResult.Failure<List<DailyDuas>>(Connectivity()),
            exactly = 1
        )
    }

    @Test
    fun testLoadDeliversUnexpectedError() = runBlocking {
        expect(
            sut = sut,
            receivedResult = FirestoreClientResult.Failure(UnexpectedException()),
            expectedResult = FirestoreDomainResult.Failure<List<DailyDuas>>(Unexpected()),
            exactly = 1
        )
    }

    @Test
    fun testLoadDeliversSuccess() = runBlocking {
        val models = listOf(
            DailyDuasModel(
                id = "1",
                title = "test",
                content = "test",
                translate = "test"
            ),
            DailyDuasModel(
                id = "1",
                title = "test",
                content = "test",
                translate = "test"
            )
        )

        val domainModel = listOf(
            DailyDuas(
                id = "1",
                title = "test",
                content = "test",
                translate = "test"
            ),
            DailyDuas(
                id = "1",
                title = "test",
                content = "test",
                translate = "test"
            )
        )

        expect(
            sut = sut,
            receivedResult = FirestoreClientResult.Success(models),
            expectedResult = FirestoreDomainResult.Success(domainModel),
            exactly = 1
        )
    }

    @Test
    fun testLoadDeliversSuccessWithEmptyData() = runBlocking {
        expect(
            sut = sut,
            receivedResult = FirestoreClientResult.Success(emptyList()),
            expectedResult = FirestoreDomainResult.Success<List<DailyDuas>>(emptyList()),
            exactly = 1
        )
    }

    private fun expect(
        sut: GetDailyDuasFirestoreUseCase,
        receivedResult: FirestoreClientResult<List<DailyDuasModel>>,
        expectedResult: Any,
        exactly: Int = -1
    ) = runBlocking {
        every {
            client.getDailyDuas()
        } returns flowOf(receivedResult)

        sut.getDailyDuas().test {
            when (val received = awaitItem()) {
                is FirestoreDomainResult.Failure -> {
                    assertEquals(expectedResult::class.java, received::class.java)
                }
                is FirestoreDomainResult.Success -> {
                    assertEquals(expectedResult, received)
                }
            }

            awaitComplete()
        }

        verify(exactly = exactly) {
            client.getDailyDuas()
        }

        confirmVerified(client)
    }
}
