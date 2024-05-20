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
        every {
            client.getDailyDuas()
        } returns flowOf(FirestoreClientResult.Failure(ConnectivityException()))

        sut.getDailyDuas().test {
            when (val received = awaitItem()) {
                is FirestoreDomainResult.Failure -> {
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

    @Test
    fun testLoadDeliversUnexpectedError() = runBlocking {
        every {
            client.getDailyDuas()
        } returns flowOf(FirestoreClientResult.Failure(UnexpectedException()))

        sut.getDailyDuas().test {
            when (val received = awaitItem()) {
                is FirestoreDomainResult.Failure -> {
                    assertEquals(Unexpected()::class.java, received.exception::class.java)
                }
                else -> {}
            }

            awaitComplete()
        }

        verify(exactly = 1) {
            client.getDailyDuas()
        }

        confirmVerified()
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

        every {
            client.getDailyDuas()
        } returns flowOf(FirestoreClientResult.Success(models))

        sut.getDailyDuas().test {
            when (val received = awaitItem()) {
                is FirestoreDomainResult.Success -> {
                    assertEquals(domainModel, received.root)
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
