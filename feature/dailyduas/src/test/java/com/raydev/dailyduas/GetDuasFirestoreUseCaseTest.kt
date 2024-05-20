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
sealed class FirestoreDomainResult {
    data class Success(val root: List<DailyDuas>) : FirestoreDomainResult()
    data class Failure(val exception: Exception) : FirestoreDomainResult()
}

interface FirestoreClient {
    fun getDailyDuas(): Flow<FirestoreClientResult>
}

class GetDailyDuasFirestoreUseCase(
    private val client: FirestoreClient
) {
    fun getDailyDuas(): Flow<FirestoreDomainResult> {
        return flow {
            client.getDailyDuas().collect { result ->
                when (result) {
                    is FirestoreClientResult.Failure -> {
                        when (result.exception) {
                            is ConnectivityException -> {
                                emit(FirestoreDomainResult.Failure(Connectivity()))
                            }
                            else -> {
                                emit(FirestoreDomainResult.Failure(Unexpected()))
                            }
                        }
                    }
                    is FirestoreClientResult.Success -> {
                        emit(
                            FirestoreDomainResult.Success(
                                result.root.map {
                                    it.toDomainModels()
                                }
                            )
                        )
                    }
                }
            }
        }
    }

    fun DailyDuasModel.toDomainModels() = DailyDuas(
        id = this.id,
        title = this.title,
        content = this.content
    )
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
        expect(
            sut = sut,
            receivedResult = FirestoreClientResult.Failure(ConnectivityException()),
            expectedResult = FirestoreDomainResult.Failure(Connectivity()),
            exactly = 1
        )
    }

    @Test
    fun testLoadDeliversUnexpectedError() = runBlocking {
        expect(
            sut = sut,
            receivedResult = FirestoreClientResult.Failure(UnexpectedException()),
            expectedResult = FirestoreDomainResult.Failure(Unexpected()),
            exactly = 1
        )
    }

    @Test
    fun testLoadDeliversSuccess() = runBlocking {
        val models = listOf(
            DailyDuasModel(
                id = "1",
                title = "test",
                content = "test"
            ),
            DailyDuasModel(
                id = "1",
                title = "test",
                content = "test"
            )
        )

        val domainModel = listOf(
            DailyDuas(
                id = "1",
                title = "test",
                content = "test"
            ),
            DailyDuas(
                id = "1",
                title = "test",
                content = "test"
            )
        )

        expect(
            sut = sut,
            receivedResult = FirestoreClientResult.Success(models),
            expectedResult = FirestoreDomainResult.Success(domainModel),
            exactly = 1
        )
    }

    private fun expect(
        sut: GetDailyDuasFirestoreUseCase,
        receivedResult: FirestoreClientResult,
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
