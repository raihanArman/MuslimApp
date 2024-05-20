package com.raydev.dailyduas

import app.cash.turbine.test
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.FirebaseFirestoreException.Code.PERMISSION_DENIED
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
 * @date 20/05/24
 */

sealed class FirestoreClientResult {
    data class Success(val root: List<DailyDuasModel>) : FirestoreClientResult()
    data class Failure(val exception: Exception) : FirestoreClientResult()
}

class DailyDuasFirestoreClient(
    private val service: DailyDuasFirestoreService
) {
    fun getDailyDuas(): Flow<FirestoreClientResult> {
        return flow {
            try {
                val result = service.getDailyDuas()
                emit(
                    FirestoreClientResult.Success(
                        result.map {
                            it.toModels()
                        }
                    )
                )
            } catch (exception: Exception) {
                when (exception) {
                    is IOException -> {
                        emit(FirestoreClientResult.Failure(ConnectivityException()))
                    }
                    is FirebaseFirestoreException -> {
                        when (exception.code) {
                            FirebaseFirestoreException.Code.PERMISSION_DENIED -> {
                                emit(FirestoreClientResult.Failure(PermissionDeniedException()))
                            }
                            else -> {}
                        }
                    }
                    else -> {
                        emit(FirestoreClientResult.Failure(UnexpectedException()))
                    }
                }
            }
        }
    }

    fun DailyDuasResponse.toModels() = DailyDuasModel(
        id = this.id.orEmpty(),
        title = this.title.orEmpty(),
        content = this.content.orEmpty()
    )
}
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
                content = "test"
            ),
            DailyDuasModel(
                id = "1",
                title = "test",
                content = "test"
            )
        )

        expect(
            sut = sut,
            expectedResult = FirestoreClientResult.Success(models),
            receivedResult = responses
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

            expectedResult is FirestoreClientResult.Success -> {
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
