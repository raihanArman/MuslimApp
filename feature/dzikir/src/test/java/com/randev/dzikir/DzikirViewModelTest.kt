package com.randev.dzikir

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cash.turbine.test
import com.randev.dzikir.domain.model.Dzikir
import com.randev.dzikir.domain.request.DzikirRequest
import com.randev.dzikir.util.DzikirCategory
import com.raydev.anabstract.exception.Connectivity
import com.raydev.anabstract.state.FirestoreDomainResult
import io.mockk.MockKAnnotations
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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

interface GetDzikirUseCase {
    fun load(category: DzikirRequest): Flow<FirestoreDomainResult<List<Dzikir>>>
}

data class DzikirState(
    val isLoading: Boolean = false,
    val errorMessage: String ? = null,
    val data: List<Dzikir> ? = null,
)

class DzikirViewModel(
    private val useCase: GetDzikirUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(DzikirState())
    val uiState = _uiState.asStateFlow()

    fun load(request: DzikirRequest) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }
            useCase.load(request).collect { result ->
                when (result) {
                    is FirestoreDomainResult.Failure -> {
                        when (result.exception) {
                            is Connectivity -> {
                                _uiState.update {
                                    it.copy(
                                        isLoading = false,
                                        errorMessage = "Tidak ada koneksi"
                                    )
                                }
                            }
                        }
                    }
                    is FirestoreDomainResult.Success -> {}
                }
            }
        }
    }
}

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
    fun testLoadFailedConnectivityShowsConnectivityError() = runBlocking {
        val captureRequest = slot<DzikirRequest>()
        every {
            useCase.load(capture(captureRequest))
        } returns flowOf(FirestoreDomainResult.Failure(Connectivity()))

        sut.load(request)

        sut.uiState.take(1).test {
            val received = awaitItem()
            assertEquals(captureRequest.captured, request)
            assertEquals(false, received.isLoading)
            assertEquals("Tidak ada koneksi", received.errorMessage)
            assertEquals(null, received.data)

            awaitComplete()
        }

        verify(exactly = 1) {
            useCase.load(request)
        }

        confirmVerified(useCase)
    }
}
