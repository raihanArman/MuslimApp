package com.raydev.shortvideo

import app.cash.turbine.test
import com.raydev.anabstract.exception.Connectivity
import com.raydev.anabstract.exception.Unexpected
import com.raydev.anabstract.state.FirestoreDomainResult
import com.raydev.shortvideo.domain.GetShortVideoUseCase
import com.raydev.shortvideo.domain.ShortVideo
import com.raydev.shortvideo.presentation.GetShortVideoViewModel
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
 * @date 14/07/24
 */
class GetShortVideoViewModelTest {
    private val useCase = mockk<GetShortVideoUseCase>()
    private lateinit var sut: GetShortVideoViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        sut = GetShortVideoViewModel(useCase)
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @Test
    fun testInitState() {
        val uiState = sut.uiState.value

        assertFalse(uiState.isLoading)
        assertTrue(uiState.data == null)
        assert(uiState.errorMessage == null)
    }

    @Test
    fun testInitDoesNotLoad() {
        verify(exactly = 0) {
            useCase.getShortVideo()
        }

        confirmVerified(useCase)
    }

    @Test
    fun testLoadRequestData() {
        every {
            useCase.getShortVideo()
        } returns flowOf()

        sut.load()

        verify(exactly = 1) {
            useCase.getShortVideo()
        }

        confirmVerified(useCase)
    }

    @Test
    fun testLoadTwiceRequestData() {
        every {
            useCase.getShortVideo()
        } returns flowOf()

        sut.load()
        sut.load()

        verify(exactly = 2) {
            useCase.getShortVideo()
        }

        confirmVerified(useCase)
    }

    @Test
    fun testLoadIsLoadingState() = runBlocking {
        every {
            useCase.getShortVideo()
        } returns flowOf()

        sut.load()

        sut.uiState.take(count = 1).test {
            val received = awaitItem()
            assertTrue(received.isLoading)
            awaitComplete()
        }

        verify(exactly = 1) {
            useCase.getShortVideo()
        }

        confirmVerified(useCase)
    }

    @Test
    fun testLoadFailedConnectivityShowsConnectivityError() {
        expect(
            sut = sut,
            result = FirestoreDomainResult.Failure(Connectivity()),
            expectedFailed = "Tidak ada internet",
            expectedLoading = false
        )
    }

    @Test
    fun testLoadFailedUnexpectedShowsUnexpectedError() {
        expect(
            sut = sut,
            result = FirestoreDomainResult.Failure(Unexpected()),
            expectedFailed = "Tidak ditemukan",
            expectedLoading = false
        )
    }

    @Test
    fun testLoadShowsData() {
        val domainModel = listOf(
            ShortVideo(
                id = "1",
                title = "test",
                url = "test",
                description = "test"
            ),
            ShortVideo(
                id = "1",
                title = "test",
                url = "test",
                description = "test"
            )
        )

        expect(
            sut = sut,
            result = FirestoreDomainResult.Success(domainModel),
            expectedFailed = null,
            expectedLoading = false,
            expectedData = domainModel
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

    private fun expect(
        sut: GetShortVideoViewModel,
        result: FirestoreDomainResult<List<ShortVideo>>,
        expectedLoading: Boolean,
        expectedFailed: String?,
        expectedData: List<ShortVideo>? = null
    ) = runBlocking {
        every {
            useCase.getShortVideo()
        } returns flowOf(result)

        sut.load()

        sut.uiState.take(count = 1).test {
            val received = awaitItem()

            assertEquals(expectedLoading, received.isLoading)
            assertEquals(expectedFailed, received.errorMessage)
            assertEquals(expectedData, received.data)
            awaitComplete()
        }

        verify(exactly = 1) {
            useCase.getShortVideo()
        }

        confirmVerified(useCase)
    }
}
