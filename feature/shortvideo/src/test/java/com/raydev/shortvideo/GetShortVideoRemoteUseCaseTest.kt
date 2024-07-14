package com.raydev.shortvideo

import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

/**
 * @author Raihan Arman
 * @date 14/07/24
 */
interface GetShortVideoClient {
    fun getShortVideo()
}

class GetShortVideoRemoteUseCase

class GetShortVideoRemoteUseCaseTest {
    private val client = mockk<GetShortVideoClient>()
    private lateinit var sut: GetShortVideoRemoteUseCase

    @Before
    fun setUp() {
        sut = GetShortVideoRemoteUseCase()
    }

    @Test
    fun testInitDoesNotRequestData() {
        verify(exactly = 0) {
            client.getShortVideo()
        }

        confirmVerified(client)
    }
}
