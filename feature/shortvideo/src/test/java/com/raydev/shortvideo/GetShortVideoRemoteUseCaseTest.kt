package com.raydev.shortvideo

import app.cash.turbine.test
import com.raydev.anabstract.state.FirestoreClientResult
import com.raydev.anabstract.state.FirestoreDomainResult
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

/**
 * @author Raihan Arman
 * @date 14/07/24
 */
interface GetShortVideoClient {
    fun getShortVideo(): Flow<FirestoreClientResult<List<ShortVideoModel>>>
}

class GetShortVideoRemoteUseCase(
    private val client: GetShortVideoClient
) {
    fun getShortVideo(): Flow<FirestoreDomainResult<List<ShortVideo>>> = flow {
        val result = client.getShortVideo()
    }
}

class GetShortVideoRemoteUseCaseTest {
    private val client = mockk<GetShortVideoClient>()
    private lateinit var sut: GetShortVideoRemoteUseCase

    @Before
    fun setUp() {
        sut = GetShortVideoRemoteUseCase(client)
    }

    @Test
    fun testInitDoesNotRequestData() {
        verify(exactly = 0) {
            client.getShortVideo()
        }

        confirmVerified(client)
    }

    @Test
    fun testLoadRequestData() = runBlocking {
        every {
            client.getShortVideo()
        } returns flowOf()

        sut.getShortVideo().test {
            awaitComplete()
        }

        verify(exactly = 1) {
            client.getShortVideo()
        }

        confirmVerified()
    }
}
