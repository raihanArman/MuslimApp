package com.randev.dzikir

import app.cash.turbine.test
import com.randev.dzikir.api.DzikirModel
import com.randev.dzikir.api.DzikirRequestDto
import com.randev.dzikir.api_infra.DzikirResponse
import com.randev.dzikir.util.DzikirCategory
import com.raydev.anabstract.exception.ConnectivityException
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
        } catch (e: Exception) {
            emit(FirestoreClientResult.Failure(ConnectivityException()))
        }
    }
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
        val captureCategory = slot<String>()

        coEvery {
            service.getDzikir(capture(captureCategory))
        } throws IOException()

        sut.getDzikir(requestDto).test {
            assertEquals(requestDto.category.value, captureCategory.captured)
            when (val received = awaitItem()) {
                is FirestoreClientResult.Failure -> {
                    assertEquals(ConnectivityException()::class.java, received.exception::class.java)
                }
                is FirestoreClientResult.Success -> {}
            }

            awaitComplete()
        }

        coVerify {
            service.getDzikir(capture(captureCategory))
        }
    }
}
