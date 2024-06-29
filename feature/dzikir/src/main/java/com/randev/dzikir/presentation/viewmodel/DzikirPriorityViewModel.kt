package com.randev.dzikir.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.randev.dzikir.domain.GetDzikirPriorityUseCase
import com.raydev.anabstract.exception.Connectivity
import com.raydev.anabstract.exception.Unexpected
import com.raydev.anabstract.state.FirestoreDomainResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * @author Raihan Arman
 * @date 29/06/24
 */
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
                            is Unexpected -> {
                                _uiState.update {
                                    it.copy(isLoading = false, errorMessage = "Tidak ditemukan")
                                }
                            }
                        }
                    }
                    is FirestoreDomainResult.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                data = result.data
                            )
                        }
                    }
                }
            }
        }
    }
}
