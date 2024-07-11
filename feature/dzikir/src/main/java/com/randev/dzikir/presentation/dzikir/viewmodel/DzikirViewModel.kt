package com.randev.dzikir.presentation.dzikir.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.randev.dzikir.domain.request.DzikirRequest
import com.randev.dzikir.domain.usecase.GetDzikirUseCase
import com.raydev.anabstract.exception.Connectivity
import com.raydev.anabstract.exception.Unexpected
import com.raydev.anabstract.state.FirestoreDomainResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * @author Raihan Arman
 * @date 10/07/24
 */
class DzikirViewModel(
    private val useCase: GetDzikirUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(DzikirState())
    val uiState = _uiState.asStateFlow()

    fun load(request: DzikirRequest) {
        viewModelScope.launch(Dispatchers.IO) {
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
                                data = result.data
                            )
                        }
                    }
                }
            }
        }
    }
}
