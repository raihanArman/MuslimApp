package com.raydev.dailyduas.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raydev.dailyduas.domain.Connectivity
import com.raydev.dailyduas.domain.FirestoreDomainResult
import com.raydev.dailyduas.domain.GetDuasUseCase
import com.raydev.dailyduas.domain.Unexpected
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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
