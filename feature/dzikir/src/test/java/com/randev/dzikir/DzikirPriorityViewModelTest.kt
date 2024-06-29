package com.randev.dzikir

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cash.turbine.test
import com.randev.dzikir.domain.DzikirPriority
import com.raydev.anabstract.exception.Connectivity
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
interface GetDzikirPriorityUseCase {
    fun load(): Flow<FirestoreDomainResult<List<DzikirPriority>>>
}

data class DzikirPriorityState(
    val isLoading: Boolean = false,
    val errorMessage: String ? = null,
    val data: List<DzikirPriority> ? = null,
)

class DzikirPriorityViewModel(
    private val useCase: GetDzikirPriorityUseCase
) : ViewModel() {
    private val _uiState: MutableStateFlow<DzikirPriorityState> = MutableStateFlow(DzikirPriorityState())
    val uiState: StateFlow<DzikirPriorityState> = _uiState.asStateFlow()

    fun load() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }
            useCase.load().collect { result ->
                when (result) {
                    is FirestoreDomainResult.Failure -> {
                        when (result.exception) {
                            is Connectivity -> {
                                _uiState.update {
                                    it.copy(isLoading = false, errorMessage = "Tidak ada internet")
                                }
                            }
                        }
                    }
                    is FirestoreDomainResult.Success -> {
                    }
                }
            }
        }
    }
}

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
        every {
            useCase.load()
        } returns flowOf(FirestoreDomainResult.Failure(Connectivity()))

        sut.load()

        sut.uiState.take(count = 1).test {
            val received = awaitItem()
            assertEquals(false, received.isLoading)
            assertEquals("Tidak ada internet", received.errorMessage)

            awaitComplete()
        }

        verify(exactly = 1) {
            useCase.load()
        }
    }
}
