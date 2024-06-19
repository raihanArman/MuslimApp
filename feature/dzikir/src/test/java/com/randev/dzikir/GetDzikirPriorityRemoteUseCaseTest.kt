package com.randev.dzikir

import app.cash.turbine.test
import com.randev.dzikir.api.DzikirPriorityModel
import com.randev.dzikir.domain.DzikirPriority
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

/**
 * @author Raihan Arman
 * @date 18/06/24
 */
interface GetDzikirPriorityHttpClient {
    fun getDzikirPriority(): Flow<FirestoreClientResult<List<DzikirPriorityModel>>>
}

class GetDzikirPriorityRemoteUseCase(
    private val client: GetDzikirPriorityHttpClient
) {
    fun load(): Flow<FirestoreDomainResult<List<DzikirPriority>>> = flow {
        client.getDzikirPriority().collect { result ->
            when (result) {
                is FirestoreClientResult.Failure -> {
                    when (result.exception) {
                        is ConnectivityException -> {
                            emit(FirestoreDomainResult.Failure(Connectivity()))
                        }
                        is UnexpectedException -> {
                            emit(FirestoreDomainResult.Failure(Unexpected()))
                        }
                    }
                }
                is FirestoreClientResult.Success -> {
                    emit(
                        FirestoreDomainResult.Success(
                            result.data.map {
                                it.toDomainModels()
                            }
                        )
                    )
                }
            }
        }
    }

    private fun DzikirPriorityModel.toDomainModels() = DzikirPriority(
        id = this.id,
        content = this.content,
        translate = this.translate
    )
}

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
