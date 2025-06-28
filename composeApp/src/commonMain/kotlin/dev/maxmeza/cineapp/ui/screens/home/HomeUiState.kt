package dev.maxmeza.cineapp.ui.screens.home

import dev.maxmeza.cineapp.domain.model.User

sealed class HomeUiState {
    object isLoading : HomeUiState()
    data class Success(val data: User) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}