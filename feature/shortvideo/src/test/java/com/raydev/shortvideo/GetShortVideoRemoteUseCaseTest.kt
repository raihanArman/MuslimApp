package com.raydev.shortvideo

import app.cash.turbine.test
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
 * @date 14/07/24
 */
interface GetShortVideoClient {
    fun getShortVideo(): Flow<FirestoreClientResult<List<ShortVideoModel>>>
}

class GetShortVideoRemoteUseCase(
    private val client: GetShortVideoClient
) {
    fun getShortVideo(): Flow<FirestoreDomainResult<List<ShortVideo>>> = flow {
        client.getShortVideo().collect { result ->
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
                    val domainModel = result.data.map {
                        toDomainModel(it)
                    }

                    emit(FirestoreDomainResult.Success(domainModel))
                }
            }
        }
    }

    private fun toDomainModel(model: ShortVideoModel?) = ShortVideo(
        id = model?.id.orEmpty(),
        title = model?.title.orEmpty(),
        url = model?.url.orEmpty(),
        description = model?.description.orEmpty()
    )
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

    @Test
    fun testLoadTwiceRequestData() = runBlocking {
        every {
            client.getShortVideo()
        } returns flowOf()

        sut.getShortVideo().test {
            awaitComplete()
        }

        sut.getShortVideo().test {
            awaitComplete()
        }

        verify(exactly = 2) {
            client.getShortVideo()
        }

        confirmVerified(client)
    }

    @Test
    fun testLoadDeliversConnectivityError() {
        expect(
            sut = sut,
            receivedResult = FirestoreClientResult.Failure(ConnectivityException()),
            expectedResult = Connectivity(),
            exactly = 1
        )
    }

    @Test
    fun testLoadDeliversUnexpectedError() {
        expect(
            sut = sut,
            receivedResult = FirestoreClientResult.Failure(UnexpectedException()),
            expectedResult = Unexpected(),
            exactly = 1
        )
    }

    @Test
    fun testLoadDeliversSuccess() = runBlocking {
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

        val domainModel = listOf(
            ShortVideo(
                id = "1",
                title = "test",
                url = "test",
                description = "test"
            ),
            ShortVideo(
                id = "1",
                title = "test",
                url = "test",
                description = "test"
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
            expectedResult = emptyList<ShortVideo>(),
            exactly = 1
        )
    }

    private fun expect(
        sut: GetShortVideoRemoteUseCase,
        receivedResult: FirestoreClientResult<List<ShortVideoModel>>,
        expectedResult: Any,
        exactly: Int = -1
    ) = runBlocking {
        every {
            client.getShortVideo()
        } returns flowOf(receivedResult)

        sut.getShortVideo().test {
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
            client.getShortVideo()
        }

        confirmVerified(client)
    }
}
