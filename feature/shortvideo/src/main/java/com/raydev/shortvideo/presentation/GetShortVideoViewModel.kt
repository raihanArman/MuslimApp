package com.raydev.shortvideo.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raydev.anabstract.exception.Connectivity
import com.raydev.anabstract.exception.Unexpected
import com.raydev.anabstract.state.FirestoreDomainResult
import com.raydev.shortvideo.domain.GetShortVideoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * @author Raihan Arman
 * @date 14/07/24
 */
class GetShortVideoViewModel(
    private val useCase: GetShortVideoUseCase
) : ViewModel() {
    private val _uiState: MutableStateFlow<GetShortVideoState> = MutableStateFlow(GetShortVideoState())
    val uiState: StateFlow<GetShortVideoState> = _uiState.asStateFlow()

    fun load() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }
            useCase.getShortVideo().collect { result ->
                when (result) {
                    is FirestoreDomainResult.Failure -> {
                        when (result.exception) {
                            is Connectivity -> {
                                _uiState.update {
                                    it.copy(
                                        isLoading = false,
                                        errorMessage = "Tidak ada internet"
                                    )
                                }
                            }
                            is Unexpected -> {
                                _uiState.update {
                                    it.copy(
                                        isLoading = false,
                                        errorMessage = "Tidak ditemukan"
                                    )
                                }
                            }
                        }
                    }
                    is FirestoreDomainResult.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                data = result.data,
                                errorMessage = null
                            )
                        }
                    }
                }
            }
        }
    }
}
