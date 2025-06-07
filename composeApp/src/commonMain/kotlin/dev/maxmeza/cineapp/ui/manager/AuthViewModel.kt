package dev.maxmeza.cineapp.ui.manager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.maxmeza.cineapp.domain.model.Tokens
import dev.maxmeza.cineapp.domain.model.User
import dev.maxmeza.cineapp.domain.useCases.LoginUseCases
import dev.maxmeza.cineapp.util.AppLogger
import kotlinx.coroutines.flow.*

class AuthViewModel(
    private val loginUseCases: LoginUseCases
): ViewModel() {
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState = _uiState.asStateFlow()

    suspend fun login(email: String, password: String) {
       loginUseCases.invoke(email = email, password = password)
           .onStart {
               _uiState.update { AuthUiState(isLoading = true) }
           }.onEach { result ->
              result.onSuccess { data ->
                  _uiState.update { AuthUiState(user = data.user, tokens = data.tokens) }
              }.onFailure { error ->
                  _uiState.update { AuthUiState(error = error.message.toString()) }
              }
           }.launchIn(viewModelScope)
    }

    data class AuthUiState(
        val isLoading: Boolean = false,
        val error: String = "",
        val user: User? = null,
        val tokens: Tokens? = null
    )

}

