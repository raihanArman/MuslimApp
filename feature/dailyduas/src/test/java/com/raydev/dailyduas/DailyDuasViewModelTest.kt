package com.raydev.dailyduas

import app.cash.turbine.test
import com.raydev.anabstract.exception.Connectivity
import com.raydev.anabstract.exception.Unexpected
import com.raydev.anabstract.state.FirestoreDomainResult
import com.raydev.dailyduas.domain.DailyDuas
import com.raydev.dailyduas.domain.GetDuasUseCase
import com.raydev.dailyduas.presentation.viewmodel.DailyDuasViewModel
import io.mockk.MockKAnnotations
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

/**
 * @author Raihan Arman
 * @date 21/05/24
 */

class DailyDuasViewModelTest {
    private val useCase = mockk<GetDuasUseCase>()
    private lateinit var sut: DailyDuasViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        sut = DailyDuasViewModel(useCase)
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @Test
    fun testInitInitialState() {
        val uiState = sut.uiState.value

        assertFalse(uiState.isLoading)
        assertTrue(uiState.data == null)
        assert(uiState.errorMessage == null)
    }

    @Test
    fun testInitDoesNotLoad() {
        verify(exactly = 0) {
            useCase.getDailyDuas()
        }

        confirmVerified(useCase)
    }

    @Test
    fun testLoadRequestData() {
        every {
            useCase.getDailyDuas()
        } returns flowOf()

        sut.load()

        verify(exactly = 1) {
            useCase.getDailyDuas()
        }

        confirmVerified(useCase)
    }

    @Test
    fun testLoadTwiceRequestData() {
        every {
            useCase.getDailyDuas()
        } returns flowOf()

        sut.load()
        sut.load()

        verify(exactly = 2) {
            useCase.getDailyDuas()
        }

        confirmVerified(useCase)
    }

    @Test
    fun testLoadIsLoadingState() = runBlocking {
        every {
            useCase.getDailyDuas()
        } returns flowOf()

        sut.load()

        sut.uiState.take(count = 1).test {
            val received = awaitItem()
            assertTrue(received.isLoading)
            awaitComplete()
        }

        verify(exactly = 1) {
            useCase.getDailyDuas()
        }
    }

    @Test
    fun testLoadFailedConnectivityShowsConnectivityError() = runBlocking {
        expect(
            sut = sut,
            result = FirestoreDomainResult.Failure(Connectivity()),
            expectedFailed = "Tidak ada internet",
            expectedLoading = false
        )
    }

    @Test
    fun testLoadFailedUnexpectedShowsUnexpectedError() = runBlocking {
        expect(
            sut = sut,
            result = FirestoreDomainResult.Failure(Unexpected()),
            expectedFailed = "Tidak ditemukan",
            expectedLoading = false
        )
    }

    @Test
    fun testLoadShowsData() = runBlocking {
        expect(
            sut = sut,
            result = FirestoreDomainResult.Success(domainModels()),
            expectedFailed = null,
            expectedLoading = false,
            expectedData = domainModels()
        )
    }

    @Test
    fun testLoadShowsWithEmptyData() = runBlocking {
        expect(
            sut = sut,
            result = FirestoreDomainResult.Success(emptyList()),
            expectedFailed = null,
            expectedLoading = false,
            expectedData = emptyList()
        )
    }

    fun domainModels() = listOf(
        DailyDuas(
            id = "1",
            title = "test",
            content = "test",
            translate = "test"
        ),
        DailyDuas(
            id = "1",
            title = "test",
            content = "test",
            translate = "test"
        )
    )

    private fun expect(
        sut: DailyDuasViewModel,
        result: FirestoreDomainResult<List<DailyDuas>>,
        expectedLoading: Boolean,
        expectedFailed: String?,
        expectedData: List<DailyDuas>? = null
    ) = runBlocking {
        every {
            useCase.getDailyDuas()
        } returns flowOf(result)

        sut.load()

        sut.uiState.take(1).test {
            val received = awaitItem()
            if (expectedFailed == null) {
                assertEquals(expectedLoading, received.isLoading)
                assertEquals(expectedData, received.data)
                assertEquals(expectedFailed, received.errorMessage)
            } else {
                assertEquals(expectedLoading, received.isLoading)
                assertEquals(expectedFailed, received.errorMessage)
            }
            awaitComplete()
        }

        verify(exactly = 1) {
            useCase.getDailyDuas()
        }

        confirmVerified(useCase)
    }
}
