package com.randev.dzikir

import app.cash.turbine.test
import com.randev.dzikir.api.DzikirModel
import com.randev.dzikir.domain.Dzikir
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
enum class DzikirCategory(value: String) {
    PAGI("pagi"),
    PETANG("petang")
}

data class DzikirRequestDto(
    val category: DzikirCategory
)

data class DzikirRequest(
    val category: DzikirCategory
)

interface GetDzikirFirestoreClient {
    fun getDzikir(request: DzikirRequestDto): Flow<FirestoreClientResult<List<DzikirModel>>>
}

class GetDzikirFirestoreUseCase(
    private val client: GetDzikirFirestoreClient
) {
    fun load(request: DzikirRequest): Flow<FirestoreDomainResult<List<Dzikir>>> = flow {
        client.getDzikir(toDtoRequest(request)).collect { result ->
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
                is FirestoreClientResult.Success -> {}
            }
        }
    }

    private fun toDtoRequest(request: DzikirRequest) = DzikirRequestDto(
        category = request.category
    )
}

class GetDzikirFirestoreUseCaseTest {
    private val client = mockk<GetDzikirFirestoreClient>()
    private lateinit var sut: GetDzikirFirestoreUseCase

    @Before
    fun setUp() {
        sut = GetDzikirFirestoreUseCase(client)
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
        val captureRequestDto = slot<DzikirRequestDto>()

        every {
            client.getDzikir(capture(captureRequestDto))
        } returns flowOf(FirestoreClientResult.Failure(ConnectivityException()))

        sut.load(request).test {
            assertEquals(requestDto, captureRequestDto.captured)
            when (val received = awaitItem()) {
                is FirestoreDomainResult.Failure -> {
                    assertEquals(Connectivity()::class.java, received.exception::class.java)
                }
                is FirestoreDomainResult.Success -> {}
            }

            awaitComplete()
        }

        verify(exactly = 1) {
            client.getDzikir(captureRequestDto.captured)
        }
    }

    @Test
    fun testDeliversUnexpectedErrorOnClientError() = runBlocking {
        val captureRequestDto = slot<DzikirRequestDto>()

        every {
            client.getDzikir(capture(captureRequestDto))
        } returns flowOf(FirestoreClientResult.Failure(UnexpectedException()))

        sut.load(request).test {
            assertEquals(requestDto, captureRequestDto.captured)
            when (val received = awaitItem()) {
                is FirestoreDomainResult.Failure -> {
                    assertEquals(Unexpected()::class.java, received.exception::class.java)
                }
                is FirestoreDomainResult.Success -> {}
            }

            awaitComplete()
        }

        verify(exactly = 1) {
            client.getDzikir(capture(captureRequestDto))
        }
    }
}
