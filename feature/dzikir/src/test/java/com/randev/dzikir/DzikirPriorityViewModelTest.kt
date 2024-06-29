package com.randev.dzikir

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.randev.dzikir.domain.DzikirPriority
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
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
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
            useCase.load()
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
}
