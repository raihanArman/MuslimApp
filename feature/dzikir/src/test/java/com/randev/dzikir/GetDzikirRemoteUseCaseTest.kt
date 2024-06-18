package com.randev.dzikir

import app.cash.turbine.test
import com.randev.dzikir.api.DzikirModel
import com.randev.dzikir.api.DzikirRequestDto
import com.randev.dzikir.api.GetDzikirRemoteUseCase
import com.randev.dzikir.api.GetDzikirHttpClient
import com.randev.dzikir.domain.Dzikir
import com.randev.dzikir.domain.DzikirRequest
import com.randev.dzikir.util.DzikirCategory
import com.raydev.anabstract.exception.Connectivity
import com.raydev.anabstract.exception.ConnectivityException
import com.raydev.anabstract.exception.Unexpected
import com.raydev.anabstract.exception.UnexpectedException
import com.raydev.anabstract.state.FirestoreClientResult
import com.raydev.anabstract.state.FirestoreDomainResult
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
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
class GetDzikirRemoteUseCaseTest {
    private val client = mockk<GetDzikirHttpClient>()
    private lateinit var sut: GetDzikirRemoteUseCase

    @Before
    fun setUp() {
        sut = GetDzikirRemoteUseCase(client)
    }

    val requestDto = DzikirRequestDto(category = DzikirCategory.PAGI)
    val request = DzikirRequest(category = DzikirCategory.PAGI)

    @Test
    fun testInitDoesNotLoad() {
        verify(exactly = 0) {
            client.getDzikir(requestDto)
        }

        confirmVerified(client)
    }

    @Test
    fun testLoadRequestData() = runBlocking {
        every {
            client.getDzikir(requestDto)
        } returns flowOf()

        sut.load(request).test {
            awaitComplete()
        }

        verify(exactly = 1) {
            client.getDzikir(requestDto)
        }
    }

    @Test
    fun testLoadRequestDataTwice() = runBlocking {
        every {
            client.getDzikir(requestDto)
        } returns flowOf()

        sut.load(request).test {
            awaitComplete()
        }

        sut.load(request).test {
            awaitComplete()
        }

        verify(exactly = 2) {
            client.getDzikir(requestDto)
        }
    }

    @Test
    fun testDeliversConnectivityErrorOnClientError() = runBlocking {
        expect(
            sut = sut,
            receivedResult = FirestoreClientResult.Failure(ConnectivityException()),
            expectedResult = Connectivity(),
            exactly = 1
        )
    }

    @Test
    fun testDeliversUnexpectedErrorOnClientError() = runBlocking {
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
            DzikirModel(
                id = "1",
                title = "test",
                content = "test",
                translate = "test"
            ),
            DzikirModel(
                id = "1",
                title = "test",
                content = "test",
                translate = "test"
            )
        )

        val domainModel = listOf(
            Dzikir(
                id = "1",
                title = "test",
                content = "test",
                translate = "test"
            ),
            Dzikir(
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
    fun testLoadDeliversSuccessWithEmptyData() {
        expect(
            sut = sut,
            receivedResult = FirestoreClientResult.Success(emptyList()),
            expectedResult = FirestoreDomainResult.Success<List<Dzikir>>(emptyList()),
            exactly = 1
        )
    }

    private fun expect(
        sut: GetDzikirRemoteUseCase,
        receivedResult: FirestoreClientResult<List<DzikirModel>>,
        expectedResult: Any,
        exactly: Int = -1
    ) = runBlocking {
        val captureRequestDto = slot<DzikirRequestDto>()

        every {
            client.getDzikir(capture(captureRequestDto))
        } returns flowOf(receivedResult)

        sut.load(request).test {
            assertEquals(requestDto, captureRequestDto.captured)
            when (val received = awaitItem()) {
                is FirestoreDomainResult.Failure -> {
                    assertEquals(expectedResult::class.java, received.exception::class.java)
                }
                is FirestoreDomainResult.Success -> {
                    assertEquals(expectedResult, received)
                }
            }

            awaitComplete()
        }

        verify(exactly = exactly) {
            client.getDzikir(capture(captureRequestDto))
        }
    }
}
