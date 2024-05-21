package com.raydev.dailyduas

import com.raydev.dailyduas.domain.GetDuasUseCase
import com.raydev.dailyduas.presentation.viewmodel.DailyDuasState
import io.mockk.MockKAnnotations
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
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
) {
    private val _uiState: MutableStateFlow<DailyDuasState> = MutableStateFlow(DailyDuasState())
    val uiState: StateFlow<DailyDuasState> = _uiState.asStateFlow()

    fun load() {
        useCase.getDailyDuas()
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
}
