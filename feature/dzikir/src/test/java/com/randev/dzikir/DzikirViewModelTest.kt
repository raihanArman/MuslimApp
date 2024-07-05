package com.randev.dzikir

import androidx.lifecycle.ViewModel
import com.randev.dzikir.domain.model.Dzikir
import com.randev.dzikir.domain.request.DzikirRequest
import com.randev.dzikir.util.DzikirCategory
import com.raydev.anabstract.state.FirestoreDomainResult
import io.mockk.MockKAnnotations
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
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

class DzikirViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DzikirState())
    val uiState = _uiState.asStateFlow()
}

class DzikirViewModelTest {
    private val useCase = mockk<GetDzikirUseCase>()
    private lateinit var sut: DzikirViewModel

    val request = DzikirRequest(category = DzikirCategory.PAGI)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        sut = DzikirViewModel()
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
}
