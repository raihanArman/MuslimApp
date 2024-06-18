package com.randev.dzikir

import com.randev.dzikir.api.DzikirModel
import com.raydev.anabstract.state.FirestoreClientResult
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.Flow
import org.junit.Before
import org.junit.Test

/**
 * @author Raihan Arman
 * @date 18/06/24
 */
interface GetDzikirPriorityHttpClient {
    fun getDzikirPriority(): Flow<FirestoreClientResult<List<DzikirModel>>>
}

class GetDzikirPriorityRemoteUseCase

class GetDzikirPriorityRemoteUseCaseTest {
    private val client: GetDzikirPriorityHttpClient = mockk()
    private lateinit var sut: GetDzikirPriorityRemoteUseCase

    @Before
    fun setUp() {
        sut = GetDzikirPriorityRemoteUseCase()
    }

    @Test
    fun testInitDoesNotLoad() {
        verify(exactly = 0) {
            client.getDzikirPriority()
        }
    }
}
