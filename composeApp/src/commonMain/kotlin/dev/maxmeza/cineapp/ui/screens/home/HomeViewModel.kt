package dev.maxmeza.cineapp.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.maxmeza.cineapp.domain.model.User
import dev.maxmeza.cineapp.domain.useCases.HomeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

class HomeViewModel(
    private val homeUseCase: HomeUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.isLoading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    suspend fun getDemoUser() {
        homeUseCase.getDemo()
            .onStart {
                _uiState.value = HomeUiState.isLoading
            }.onEach { result ->
                result.onSuccess { data ->
                    _uiState.value = HomeUiState.Success(data)
                }.onFailure { error ->
                    _uiState.value = HomeUiState.Error(error.message.toString())
                }
            }
            .launchIn(viewModelScope)
    }

}