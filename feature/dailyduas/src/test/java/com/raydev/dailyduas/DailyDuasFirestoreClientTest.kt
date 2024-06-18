package com.raydev.dailyduas

import app.cash.turbine.test
import com.raydev.anabstract.exception.ConnectivityException
import com.raydev.anabstract.state.FirestoreClientResult
import com.raydev.dailyduas.api.DailyDuasModel
import com.raydev.dailyduas.api_infra.DailyDuasFirestoreClient
import com.raydev.dailyduas.api_infra.DailyDuasFirestoreService
import com.raydev.dailyduas.api_infra.DailyDuasResponse
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
 * @date 20/05/24
 */

class DailyDuasFirestoreClientTest {
    private val service = mockk<DailyDuasFirestoreService>(relaxed = true)
    private lateinit var sut: DailyDuasFirestoreClient

    @Before
    fun setUp() {
        sut = DailyDuasFirestoreClient(service)
    }

    @Test
    fun testGetFailsOnConnectivity() = runBlocking {
        expect(
            sut = sut,
            expectedResult = com.raydev.anabstract.exception.ConnectivityException()
        )
    }

    @Test
    fun testGetFailsOnUnexpected() = runBlocking {
        expect(
            sut = sut,
            expectedResult = com.raydev.anabstract.exception.UnexpectedException()
        )
    }

    @Test
    fun testGetSuccessResponse() = runBlocking {
        val responses = listOf(
            DailyDuasResponse(
                id = "1",
                title = "test",
                content = "test"
            ),
            DailyDuasResponse(
                id = "1",
                title = "test",
                content = "test"
            )
        )

        val models = listOf(
            DailyDuasModel(
                id = "1",
                title = "test",
                content = "test",
                translate = "test"
            ),
            DailyDuasModel(
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
    fun testGetsSuccessWithEmptyResponse() = runBlocking {
        expect(
            sut = sut,
            expectedResult = FirestoreClientResult.Success<List<DailyDuasModel>>(emptyList()),
            receivedResult = emptyList<DailyDuasResponse>()
        )
    }

    private fun expect(
        sut: DailyDuasFirestoreClient,
        receivedResult: Any? = null,
        expectedResult: Any
    ) = runBlocking {
        when {
            expectedResult is ConnectivityException -> {
                coEvery {
                    service.getDailyDuas()
                } throws IOException()
            }

            expectedResult is FirestoreClientResult.Success<*> -> {
                coEvery {
                    service.getDailyDuas()
                } returns receivedResult as List<DailyDuasResponse>
            }

            else -> {
                coEvery {
                    service.getDailyDuas()
                } throws Exception()
            }
        }

        sut.getDailyDuas().test {
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
            service.getDailyDuas()
        }

        confirmVerified(service)
    }

//    @Test
//    fun testGetFailsOnPermissionDenied() = runBlocking {
//        coEvery { service.getDailyDuas() } throws FirebaseFirestoreException(
//            "Permission denied",
//            FirebaseFirestoreException.Code.PERMISSION_DENIED
//        )
//
//        sut.getDailyDuas().test {
//            when(val received = awaitItem()) {
//                is FirestoreClientResult.Failure -> {
//                    assertEquals(PermissionDeniedException()::class.java, received.exception::class.java)
//                }
//                else -> {}
//            }
//
//            awaitComplete()
//        }
//
//        coVerify(exactly = 1) {
//            service.getDailyDuas()
//        }
//
//        confirmVerified(service)
//    }
}
