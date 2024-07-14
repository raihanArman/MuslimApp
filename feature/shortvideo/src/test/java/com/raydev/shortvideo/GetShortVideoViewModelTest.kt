package com.raydev.shortvideo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raydev.anabstract.state.FirestoreDomainResult
import com.raydev.shortvideo.domain.ShortVideo
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
 * @date 14/07/24
 */
interface GetShortVideoUseCase {
    fun getShortVideo(): Flow<FirestoreDomainResult<List<ShortVideo>>>
}

data class GetShortVideoState(
    val isLoading: Boolean = false,
    val errorMessage: String ? = null,
    val data: List<ShortVideo> ? = null,
)

class GetShortVideoViewModel(
    private val useCase: GetShortVideoUseCase
) : ViewModel() {
    private val _uiState: MutableStateFlow<GetShortVideoState> = MutableStateFlow(GetShortVideoState())
    val uiState: StateFlow<GetShortVideoState> = _uiState.asStateFlow()

    fun load() {
        viewModelScope.launch {
            useCase.getShortVideo()
        }
    }
}

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
}
