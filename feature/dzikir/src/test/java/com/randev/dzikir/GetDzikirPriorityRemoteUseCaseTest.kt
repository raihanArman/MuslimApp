package com.randev.dzikir

import app.cash.turbine.test
import com.randev.dzikir.api.DzikirPriorityModel
import com.randev.dzikir.domain.DzikirPriority
import com.raydev.anabstract.exception.Connectivity
import com.raydev.anabstract.exception.ConnectivityException
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
                    emit(FirestoreDomainResult.Failure(Connectivity()))
                }
                is FirestoreClientResult.Success -> {}
            }
        }
    }
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
    fun testDeliversConnectivityErrorOnClientError() = runBlocking {
        every {
            client.getDzikirPriority()
        } returns flowOf(FirestoreClientResult.Failure(ConnectivityException()))

        sut.load().test {
            when (val received = awaitItem()) {
                is FirestoreDomainResult.Failure -> {
                    assertEquals(Connectivity()::class.java, received.exception::class.java)
                }
                is FirestoreDomainResult.Success -> {}
            }

            awaitComplete()
        }

        verify(exactly = 1) {
            client.getDzikirPriority()
        }

        confirmVerified(client)
    }
}
