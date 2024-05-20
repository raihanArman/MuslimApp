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
    data class Success(val root: List<DailyDuasResponse>) : FirestoreClientResult()
    data class Failure(val exception: Exception) : FirestoreClientResult()
}

class DailyDuasFirestoreClient(
    private val service: DailyDuasFirestoreService
) {
    fun getDailyDuas(): Flow<FirestoreClientResult> {
        return flow {
            try {
                service.getDailyDuas()
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
                else -> {}
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
