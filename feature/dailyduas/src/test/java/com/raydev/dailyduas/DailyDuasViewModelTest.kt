package com.raydev.dailyduas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cash.turbine.test
import com.raydev.dailyduas.domain.Connectivity
import com.raydev.dailyduas.domain.DailyDuas
import com.raydev.dailyduas.domain.FirestoreDomainResult
import com.raydev.dailyduas.domain.GetDuasUseCase
import com.raydev.dailyduas.domain.Unexpected
import com.raydev.dailyduas.presentation.viewmodel.DailyDuasState
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
import org.junit.Before
import org.junit.Test

/**
 * @author Raihan Arman
 * @date 21/05/24
 */
class DailyDuasViewModel(
    private val useCase: GetDuasUseCase
) : ViewModel() {
    private val _uiState: MutableStateFlow<DailyDuasState> = MutableStateFlow(DailyDuasState())
    val uiState: StateFlow<DailyDuasState> = _uiState.asStateFlow()

    fun load() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }
            useCase.getDailyDuas().collect { result ->
                when (result) {
                    is FirestoreDomainResult.Failure -> {
                        when (result.exception) {
                            is Connectivity -> {
                                _uiState.update {
                                    it.copy(
                                        errorMessage = "Tidak ada internet",
                                        isLoading = false
                                    )
                                }
                            }
                            is Unexpected -> {
                                _uiState.update {
                                    it.copy(
                                        errorMessage = "Tidak ditemukan",
                                        isLoading = false
                                    )
                                }
                            }
                        }
                    }
                    is FirestoreDomainResult.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                data = result.root,
                                errorMessage = null
                            )
                        }
                    }
                }
            }
        }
    }
}

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
            expectedLoading = false
        )
    }

    fun domainModels() = listOf(
        DailyDuas(
            id = "1",
            title = "test",
            content = "test"
        ),
        DailyDuas(
            id = "1",
            title = "test",
            content = "test"
        )
    )

    private fun expect(
        sut: DailyDuasViewModel,
        result: FirestoreDomainResult,
        expectedLoading: Boolean,
        expectedFailed: String?
    ) = runBlocking {
        every {
            useCase.getDailyDuas()
        } returns flowOf(result)

        sut.load()

        sut.uiState.take(1).test {
            val received = awaitItem()
            if (expectedFailed == null) {
                assertEquals(expectedLoading, received.isLoading)
                assertEquals(domainModels(), received.data)
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
