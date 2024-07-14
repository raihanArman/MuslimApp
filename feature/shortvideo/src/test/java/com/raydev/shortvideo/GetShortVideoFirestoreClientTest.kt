package com.raydev.shortvideo

import app.cash.turbine.test
import com.raydev.anabstract.exception.ConnectivityException
import com.raydev.anabstract.exception.UnexpectedException
import com.raydev.anabstract.state.FirestoreClientResult
import com.raydev.shortvideo.api.ShortVideoModel
import com.raydev.shortvideo.api_infra.GetShortVideoFirestoreClient
import com.raydev.shortvideo.api_infra.ShortVideoResponse
import com.raydev.shortvideo.api_infra.ShortVideoService
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
 * @date 14/07/24
 */
class GetShortVideoFirestoreClientTest {
    private val service = mockk<ShortVideoService>()
    private lateinit var sut: GetShortVideoFirestoreClient

    @Before
    fun setuUp() {
        sut = GetShortVideoFirestoreClient(service)
    }

    @Test
    fun testGetFailsOnConnectivity() = runBlocking {
        expect(
            sut = sut,
            expectedResult = ConnectivityException()
        )
    }

    @Test
    fun testGetFailsUnExpected() = runBlocking {
        expect(
            sut = sut,
            expectedResult = UnexpectedException()
        )
    }

    @Test
    fun testGetSuccessResponse() {
        val models = listOf(
            ShortVideoModel(
                id = "1",
                title = "test",
                url = "test",
                description = "test"
            ),
            ShortVideoModel(
                id = "1",
                title = "test",
                url = "test",
                description = "test"
            )
        )

        val response = listOf(
            ShortVideoResponse(
                id = "1",
                title = "test",
                url = "test",
                description = "test"
            ),
            ShortVideoResponse(
                id = "1",
                title = "test",
                url = "test",
                description = "test"
            )
        )

        expect(
            sut = sut,
            receivedResult = response,
            expectedResult = FirestoreClientResult.Success(models),
        )
    }

    @Test
    fun testGetsSuccessWithEmptyResponse() {
        expect(
            sut = sut,
            receivedResult = emptyList<ShortVideoResponse>(),
            expectedResult = FirestoreClientResult.Success(emptyList<ShortVideoModel>()),
        )
    }

    private fun expect(
        sut: GetShortVideoFirestoreClient,
        receivedResult: Any? = null,
        expectedResult: Any
    ) = runBlocking {
        when {
            expectedResult is ConnectivityException -> {
                coEvery {
                    service.getShortVideo()
                } throws IOException()
            }

            expectedResult is FirestoreClientResult.Success<*> -> {
                coEvery {
                    service.getShortVideo()
                } returns receivedResult as List<ShortVideoResponse>
            }

            else -> {
                coEvery {
                    service.getShortVideo()
                } throws Exception()
            }
        }

        sut.getShortVideo().test {
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
            service.getShortVideo()
        }

        confirmVerified(service)
    }
}
