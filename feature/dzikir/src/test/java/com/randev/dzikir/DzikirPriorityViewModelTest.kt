package com.randev.dzikir

import app.cash.turbine.test
import com.randev.dzikir.domain.DzikirPriority
import com.randev.dzikir.domain.GetDzikirPriorityUseCase
import com.randev.dzikir.presentation.viewmodel.DzikirPriorityViewModel
import com.raydev.anabstract.exception.Connectivity
import com.raydev.anabstract.exception.Unexpected
import com.raydev.anabstract.state.FirestoreDomainResult
import io.mockk.MockKAnnotations
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * @author Raihan Arman
 * @date 21/06/24
 */
class DzikirPriorityViewModelTest {
    private val useCase: GetDzikirPriorityUseCase = mockk()
    private lateinit var sut: DzikirPriorityViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        sut = DzikirPriorityViewModel(useCase)
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @Test
    fun testInitialState() {
        val uiState = sut.uiState.value

        assertFalse(uiState.isLoading)
        assertTrue(uiState.data == null)
        assert(uiState.errorMessage == null)
    }

    @Test
    fun testInitDoesNotLoad() {
        verify(exactly = 0) {
            useCase.load()
        }

        confirmVerified(useCase)
    }

    @Test
    fun testLoadRequestData() {
        every {
            useCase.load()
        } returns flowOf()

        sut.load()

        verify(exactly = 1) {
            useCase.load()
        }

        confirmVerified(useCase)
    }

    @Test
    fun testLoadIsLoadingState() = runBlocking {
        every {
            useCase.load()
        } returns flowOf()

        sut.load()

        sut.uiState.take(count = 1).test {
            val received = awaitItem()
            assertTrue(received.isLoading)
            awaitComplete()
        }

        verify(exactly = 1) {
            useCase.load()
        }
    }

    @Test
    fun testLoadFailedConnectivityShowsConnectivityError() = runBlocking {
        expect(
            sut = sut,
            result = FirestoreDomainResult.Failure(Connectivity()),
            expectedLoading = false,
            expectedFailed = "Tidak ada internet"
        )
    }

    @Test
    fun testLoadFailedUnexpectedShowsUnexpectedError() {
        expect(
            sut = sut,
            result = FirestoreDomainResult.Failure(Unexpected()),
            expectedLoading = false,
            expectedFailed = "Tidak ditemukan"
        )
    }

    @Test
    fun testLoadShowsData() {
        expect(
            sut = sut,
            result = FirestoreDomainResult.Success(domainModels()),
            expectedLoading = false,
            expectedFailed = null,
            expectedData = domainModels()
        )
    }

    @Test
    fun testLoadShowsWithEmptyData() {
        expect(
            sut = sut,
            result = FirestoreDomainResult.Success(emptyList()),
            expectedLoading = false,
            expectedData = emptyList(),
            expectedFailed = null
        )
    }

    fun domainModels() = listOf(
        DzikirPriority(
            id = "1",
            content = "test",
            translate = "test"
        ),
        DzikirPriority(
            id = "1",
            content = "test",
            translate = "test"
        )
    )

    private fun expect(
        sut: DzikirPriorityViewModel,
        result: FirestoreDomainResult<List<DzikirPriority>>,
        expectedLoading: Boolean,
        expectedFailed: String?,
        expectedData: List<DzikirPriority> ? = null
    ) = runBlocking {
        every {
            useCase.load()
        } returns flowOf(result)

        sut.load()

        sut.uiState.take(count = 1).test {
            val received = awaitItem()
            assertEquals(expectedLoading, received.isLoading)
            assertEquals(expectedData, received.data)
            assertEquals(expectedFailed, received.errorMessage)

            awaitComplete()
        }

        verify(exactly = 1) {
            useCase.load()
        }

        confirmVerified(useCase)
    }
}
