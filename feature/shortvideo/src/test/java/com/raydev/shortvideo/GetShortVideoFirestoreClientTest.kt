package com.raydev.shortvideo

import app.cash.turbine.test
import com.raydev.anabstract.exception.ConnectivityException
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
 * @date 14/07/24
 */
interface ShortVideoService {
    fun getShortVideo(): List<ShortVideoResponse>
}

class GetShortVideoFirestoreClient(
    private val service: ShortVideoService
) {
    fun getShortVideo(): Flow<FirestoreClientResult<List<ShortVideoModel>>> = flow {
        try {
            val result = service.getShortVideo()
        } catch (e: Exception) {
            when (e) {
                is IOException -> {
                    emit(FirestoreClientResult.Failure(ConnectivityException()))
                }
            }
        }
    }
}

class GetShortVideoFirestoreClientTest {
    private val service = mockk<ShortVideoService>()
    private lateinit var sut: GetShortVideoFirestoreClient

    @Before
    fun setuUp() {
        sut = GetShortVideoFirestoreClient(service)
    }

    @Test
    fun testGetFailsOnConnectivity() = runBlocking {
        coEvery {
            service.getShortVideo()
        } throws IOException()

        sut.getShortVideo().test {
            when (val received = awaitItem()) {
                is FirestoreClientResult.Failure -> {
                    assertEquals(ConnectivityException()::class.java, received.exception::class.java)
                }
                is FirestoreClientResult.Success -> {
                }
            }

            awaitComplete()
        }

        coVerify {
            service.getShortVideo()
        }

        confirmVerified(service)
    }
}
