package com.randev.dzikir

import app.cash.turbine.test
import com.randev.dzikir.domain.model.Dzikir
import com.randev.dzikir.domain.request.DzikirRequest
import com.randev.dzikir.domain.usecase.GetDzikirUseCase
import com.randev.dzikir.presentation.dzikir.viewmodel.DzikirViewModel
import com.randev.dzikir.util.DzikirCategory
import com.raydev.anabstract.exception.Connectivity
import com.raydev.anabstract.exception.Unexpected
import com.raydev.anabstract.state.FirestoreDomainResult
import io.mockk.MockKAnnotations
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * @author Raihan Arman
 * @date 05/07/24
 */
class DzikirViewModelTest {
    private val useCase = mockk<GetDzikirUseCase>()
    private lateinit var sut: DzikirViewModel

    val request = DzikirRequest(category = DzikirCategory.PAGI)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        sut = DzikirViewModel(useCase)
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
            useCase.load(request)
        }

        confirmVerified(useCase)
    }

    @Test
    fun testLoadIsLoadingState() = runBlocking {
        val captureRequest = slot<DzikirRequest>()
        every {
            useCase.load(capture(captureRequest))
        } returns flowOf()

        sut.load(request = request)

        sut.uiState.take(count = 1).test {
            val received = awaitItem()
            assertEquals(captureRequest.captured, request)

            assertTrue(received.isLoading)
            awaitComplete()
        }

        verify(exactly = 1) {
            useCase.load(capture(captureRequest))
        }
    }

    @Test
    fun testLoadFailedConnectivityShowsConnectivityError() {
        expect(
            sut = sut,
            result = FirestoreDomainResult.Failure(Connectivity()),
            expectedLoading = false,
            expectedFailed = "Tidak ada koneksi",
        )
    }

    @Test
    fun testLoadFailedUnexpectedShowsUnexpectedError() {
        expect(
            sut = sut,
            result = FirestoreDomainResult.Failure(Unexpected()),
            expectedLoading = false,
            expectedFailed = "Tidak ditemukan",
        )
    }

    @Test
    fun testLoadShowsData() {
        fun domainModels() = listOf(
            Dzikir(
                id = "1",
                content = "test",
                translate = "test",
                title = "test",
                times = "3"
            ),
            Dzikir(
                id = "1",
                content = "test",
                translate = "test",
                title = "test",
                times = "3"
            )
        )

        expect(
            sut = sut,
            result = FirestoreDomainResult.Success(domainModels()),
            expectedLoading = false,
            expectedFailed = null,
            expectedData = domainModels()
        )
    }

    @Test
    fun testLoadShowsEmptyData() {
        expect(
            sut = sut,
            result = FirestoreDomainResult.Success(emptyList()),
            expectedLoading = false,
            expectedFailed = null,
            expectedData = emptyList()
        )
    }

    private fun expect(
        sut: DzikirViewModel,
        result: FirestoreDomainResult<List<Dzikir>>,
        expectedLoading: Boolean,
        expectedFailed: String?,
        expectedData: List<Dzikir> ? = null
    ) = runBlocking {
        val captureRequest = slot<DzikirRequest>()
        every {
            useCase.load(capture(captureRequest))
        } returns flowOf(result)

        sut.load(request)

        sut.uiState.take(1).test {
            val received = awaitItem()
            assertEquals(captureRequest.captured, request)
            assertEquals(expectedLoading, received.isLoading)
            assertEquals(expectedFailed, received.errorMessage)
            assertEquals(expectedData, received.data)

            awaitComplete()
        }

        verify(exactly = 1) {
            useCase.load(request)
        }

        confirmVerified(useCase)
    }
}
