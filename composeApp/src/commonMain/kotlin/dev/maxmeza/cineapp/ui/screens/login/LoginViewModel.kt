package dev.maxmeza.cineapp.ui.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dev.maxmeza.cineapp.util.AppLogger

class LoginViewModel : ViewModel() {
    var uiState by mutableStateOf(LoginUiState())
        private set

    fun validateEmail() {
        uiState = uiState.copy(
            emailError = when {
                uiState.email.isBlank() || uiState.email.isEmpty() -> "El correo electrónico es requerido"
//                !uiState.email.matches(Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")) -> "El correo electrónico es inválido"
                else -> null
            }
        )
        validateForm()
    }

    fun validatePassword() {
        uiState = uiState.copy(
            passwordError = when {
                uiState.email.isBlank() || uiState.email.isEmpty() -> "La contraseña es requerido"
                uiState.password.length in 0..<MIN_PASSWORD_LENGTH -> "La contraseña debe contener 6 carácteres"
                else -> null
            }
        )
        validateForm()
    }

    private fun validateForm() {
        AppLogger.i("LoginViewModel", "errorS: ${uiState.emailError} ${uiState.passwordError}")
        uiState = uiState.copy(
            isFormValid = uiState.email.isNotEmpty() && uiState.password.isNotEmpty() && uiState.emailError == null && uiState.passwordError == null
        )
    }

    fun onChangeEmail(email: String) {
        AppLogger.i("LoginViewModel", "onChangeEmail: $email")
        uiState = uiState.copy(
            email = email
        )
        validateEmail()
    }

    fun onChangePassword(password: String) {
        uiState = uiState.copy(
            password = password
        )
        validatePassword()
    }

    companion object {
        const val MIN_PASSWORD_LENGTH = 6
    }

    data class LoginUiState(
        var email: String = "",
        var emailError: String? = null,
        val password: String = "",
        val passwordError: String? = null,
        var isFormValid: Boolean = false,
        val emailFocused: Boolean = false,
        val passwordFocused: Boolean = false
    )

}

