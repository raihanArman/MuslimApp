package com.randev.dzikir

import app.cash.turbine.test
import com.randev.dzikir.api.DzikirModel
import com.randev.dzikir.api.DzikirRequestDto
import com.randev.dzikir.api_infra.DzikirResponse
import com.randev.dzikir.util.DzikirCategory
import com.raydev.anabstract.exception.ConnectivityException
import com.raydev.anabstract.exception.UnexpectedException
import com.raydev.anabstract.state.FirestoreClientResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.io.IOException

/**
 * @author Raihan Arman
 * @date 18/06/24
 */

interface DzikirFirestoreService {
    suspend fun getDzikir(category: String): List<DzikirResponse>
}

class GetDzikirFirestoreClient(
    private val service: DzikirFirestoreService
) {
    fun getDzikir(request: DzikirRequestDto): Flow<FirestoreClientResult<List<DzikirModel>>> = flow {
        try {
            val result = service.getDzikir(request.category.value)
            emit(
                FirestoreClientResult.Success(
                    result.map {
                        it.toModels()
                    },
                ),
            )
        } catch (e: Exception) {
            when (e) {
                is IOException -> {
                    emit(FirestoreClientResult.Failure(ConnectivityException()))
                }
                else -> {
                    emit(FirestoreClientResult.Failure(UnexpectedException()))
                }
            }
        }
    }

    private fun DzikirResponse.toModels() = DzikirModel(
        id = this.id.orEmpty(),
        title = this.title.orEmpty(),
        content = this.content.orEmpty(),
        translate = this.translate.orEmpty()
    )
}

class GetDzikirFirestoreClientTest {
    private val service: DzikirFirestoreService = mockk()
    private lateinit var sut: GetDzikirFirestoreClient

    val requestDto = DzikirRequestDto(category = DzikirCategory.PAGI)

    @Before
    fun setUp() {
        sut = GetDzikirFirestoreClient(service)
    }

    @Test
    fun testGetFailsOnConnectivity() = runBlocking {
        expect(
            sut = sut,
            expectedResult = ConnectivityException()
        )
    }

    @Test
    fun testGetFailsOnUnexpected() = runBlocking {
        expect(
            sut = sut,
            expectedResult = UnexpectedException()
        )
    }

    @Test
    fun testGetSuccessResponse() {
        val responses = listOf(
            DzikirResponse(
                id = "1",
                title = "test",
                content = "test",
                translate = "test"
            ),
            DzikirResponse(
                id = "1",
                title = "test",
                content = "test",
                translate = "test"
            )
        )

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

        expect(
            sut = sut,
            expectedResult = FirestoreClientResult.Success(models),
            receivedResult = responses
        )
    }

    @Test
    fun testGetsSuccessWithEmptyResponse() {
        expect(
            sut = sut,
            expectedResult = FirestoreClientResult.Success<List<DzikirModel>>(emptyList()),
            receivedResult = emptyList<DzikirResponse>()
        )
    }

    private fun expect(
        sut: GetDzikirFirestoreClient,
        receivedResult: Any? = null,
        expectedResult: Any
    ) = runBlocking {
        val captureCategory = slot<String>()

        when {
            expectedResult is ConnectivityException -> {
                coEvery {
                    service.getDzikir(capture(captureCategory))
                } throws IOException()
            }

            expectedResult is FirestoreClientResult.Success<*> -> {
                coEvery {
                    service.getDzikir(capture(captureCategory))
                } returns receivedResult as List<DzikirResponse>
            }

            else -> {
                coEvery {
                    service.getDzikir(capture(captureCategory))
                } throws Exception()
            }
        }

        sut.getDzikir(requestDto).test {
            assertEquals(requestDto.category.value, captureCategory.captured)
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
            service.getDzikir(capture(captureCategory))
        }
    }
}
