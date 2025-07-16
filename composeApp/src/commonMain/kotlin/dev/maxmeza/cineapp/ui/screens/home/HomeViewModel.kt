package dev.maxmeza.cineapp.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.maxmeza.cineapp.domain.model.User
import dev.maxmeza.cineapp.domain.useCases.HomeUseCase
import dev.maxmeza.cineapp.ui.common.UiState
import dev.maxmeza.cineapp.util.AppLogger
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class HomeViewModel(
    private val homeUseCase: HomeUseCase,
): ViewModel() {
    private val _uiState = MutableStateFlow<UiState<User>>(UiState.Loading)
    val uiState: StateFlow<UiState<User>> = _uiState.asStateFlow()

    private val _uiStateMessages = MutableStateFlow<UiState<List<String>>>(UiState.Loading)
    val uiStateMessages: StateFlow<UiState<List<String>>> = _uiStateMessages.asStateFlow()

    init {
        viewModelScope.launch {
            getDemoUser()
        }
    }

    override fun onCleared() {
        super.onCleared()
    }

    suspend fun getDemoUser() {
        homeUseCase.getDemo()
            .onStart {
                _uiState.value = UiState.Loading
            }.onEach { result ->
                result.onSuccess { data ->
                    _uiState.value = UiState.Success(data)
                }.onFailure { error ->
                    _uiState.value = UiState.Error(error.message.toString())
                }
            }
            .launchIn(viewModelScope)
    }

}