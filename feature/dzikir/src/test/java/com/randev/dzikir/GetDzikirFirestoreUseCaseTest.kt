package com.randev.dzikir

import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

/**
 * @author Raihan Arman
 * @date 18/06/24
 */
interface GetDzikirFirestoreClient {
    fun getDzikir(category: String)
}

class GetDzikirFirestoreUseCase

class GetDzikirFirestoreUseCaseTest {
    private val client = mockk<GetDzikirFirestoreClient>()
    private lateinit var sut: GetDzikirFirestoreUseCase

    @Before
    fun setUp() {
        sut = GetDzikirFirestoreUseCase()
    }

    @Test
    fun testInitDoesNotLoad() {
        verify(exactly = 0) {
            client.getDzikir(category = "")
        }

        confirmVerified(client)
    }
}
